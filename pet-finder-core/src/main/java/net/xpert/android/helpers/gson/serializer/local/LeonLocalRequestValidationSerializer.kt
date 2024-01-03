package net.xpert.android.helpers.gson.serializer.local

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import net.xpert.core.common.data.consts.Constants.CLAZZ
import net.xpert.core.common.data.consts.Constants.MESSAGE
import net.xpert.core.common.data.consts.Constants.SUBTYPE
import net.xpert.core.common.data.model.exception.LeonException
import java.lang.reflect.Type

internal class LeonLocalRequestValidationSerializer : JsonSerializer<LeonException.Local.RequestValidation> {

    override fun serialize(
        src: LeonException.Local.RequestValidation?, typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val jsonObject = JsonObject()
        src?.apply {
            jsonObject.addProperty(MESSAGE, message)
            jsonObject.addProperty(CLAZZ, clazz.java.name)
            jsonObject.addProperty(
                SUBTYPE, LeonException.Local.RequestValidation::class.java.simpleName
            )
        }
        return jsonObject
    }
}