package net.xpert.android.helpers.gson.serializer.client

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import net.xpert.core.common.data.consts.Constants.SUBTYPE
import net.xpert.core.common.data.model.exception.LeonException
import java.lang.reflect.Type

internal class LeonClientUnauthorizedSerializer :
    JsonSerializer<LeonException.Client.Unauthorized> {

    override fun serialize(
        src: LeonException.Client.Unauthorized?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(
                SUBTYPE, LeonException.Client.Unauthorized::class.java.simpleName
            )
        }
        return jsonObject
    }
}