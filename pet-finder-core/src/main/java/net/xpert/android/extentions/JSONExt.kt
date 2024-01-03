package net.xpert.android.extentions

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
 fun <M> M.toJson(): String = Gson().toJson(this)
 fun <M> String.getModelFromJSON(tokenType: Type): M = Gson().fromJson(this, tokenType)

internal fun <M> JSONObject.getModelFromJSON(tokenType: Type): M =
    this.toString().getModelFromJSON(tokenType)

internal fun <M> String.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    Gson().fromJson(this, tokenType)

internal fun <M> JSONArray.getListOfModelFromJSON(tokenType: Type): ArrayList<M> =
    this.toString().getListOfModelFromJSON(tokenType)

internal fun JSONArray.jsonArrayToHashMap(): HashMap<String, String> {
    val hashMap = HashMap<String, String>()
    for (i in 0 until length()) {
        val jsonObject = getJSONObject(i)
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.optString(key, "")
            hashMap[key] = value
        }
    }
    return hashMap
}