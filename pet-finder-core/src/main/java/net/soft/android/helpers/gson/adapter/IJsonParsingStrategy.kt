package net.soft.android.helpers.gson.adapter

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken.NULL
import com.google.gson.stream.JsonToken.STRING
import org.json.JSONArray
import org.json.JSONObject

internal interface IJsonParsingStrategy<T> {
    fun parse(reader: JsonReader): T

    fun getJsonArrayFromReader(reader: JsonReader): JSONArray {
        try {
            val errorsArray = JSONArray()
            reader.beginArray()
            while (reader.hasNext()) {
                val errorObject = JSONObject()
                reader.beginObject()
                while (reader.hasNext()) {
                    val name = reader.nextName()
                    val value = reader.nextString()
                    errorObject.put(name, value)
                }
                reader.endObject()
                errorsArray.put(errorObject)
            }
            reader.endArray()
            return errorsArray
        } catch (e: Exception) {
            when (reader.peek()) {
                //removing the null value it self and check if its
                //[STRING] or [NULL]
                STRING -> reader.nextString()
                NULL -> reader.nextNull()
                else -> {}
            }
            return JSONArray()
        }

    }
}