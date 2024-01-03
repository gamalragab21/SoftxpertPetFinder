package net.xpert.android.helpers.gson.serializer.client

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import net.xpert.core.common.data.consts.Constants.ERRORS
import net.xpert.core.common.data.consts.Constants.MESSAGE
import net.xpert.core.common.data.consts.Constants.SUBTYPE
import net.xpert.core.common.data.model.exception.LeonException
import java.lang.reflect.Type

internal class LeonClientResponseValidationSerializer :
    JsonSerializer<LeonException.Client.ResponseValidation> {

    override fun serialize(
        src: LeonException.Client.ResponseValidation?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(MESSAGE, message)
            jsonObject.addProperty(
                SUBTYPE, LeonException.Client.ResponseValidation::class.java.simpleName
            )

            // Serialize ResponseValidation-specific fields
            val errorsJson = JsonObject()
            errors.forEach { (key, value) ->
                errorsJson.addProperty(key, value)
            }
            jsonObject.add(ERRORS, errorsJson)
        }
        return jsonObject
    }
}