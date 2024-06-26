package net.soft.android.helpers.gson.parsing

import com.google.gson.stream.JsonReader
import net.soft.android.extentions.jsonArrayToHashMap
import net.soft.android.helpers.gson.adapter.IJsonParsingStrategy
import net.soft.core.common.data.consts.Constants.CODE
import net.soft.core.common.data.consts.Constants.ERRORS
import net.soft.core.common.data.consts.Constants.MESSAGE
import net.soft.core.common.data.model.exception.LeonException
import org.json.JSONArray

internal class ResponseBodyParsingStrategy : IJsonParsingStrategy<LeonException> {
    override fun parse(reader: JsonReader): LeonException {
        reader.beginObject()
        var message: String? = null
        var httpErrorCode: Int? = null
        var errors: JSONArray? = null

        while (reader.hasNext()) {
            when (reader.nextName()) {
                MESSAGE -> message = reader.nextString()
                CODE -> httpErrorCode = reader.nextInt()
                ERRORS -> errors = getJsonArrayFromReader(reader)
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        // handle any custom code
        return when (httpErrorCode) {
            422 -> LeonException.Client.ResponseValidation(
                errors = errors?.jsonArrayToHashMap() ?: hashMapOf(), message = message
            )

            else -> LeonException.Client.Unhandled(
                httpErrorCode = httpErrorCode ?: -1, message = message
            )
        }
    }
}