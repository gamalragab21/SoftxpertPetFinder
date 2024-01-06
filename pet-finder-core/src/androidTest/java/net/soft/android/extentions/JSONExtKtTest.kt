package net.soft.android.extentions


import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun testToJson() {
        val sampleObject = TestModel("test")
        val json = sampleObject.toJson()
        assertEquals("{\"property\":\"test\"}", json)
    }

    @Test
    fun testGetModelFromJSON() {
        val json = "{\"property\":\"test\"}"
        val typeToken = object : TypeToken<TestModel>() {}.type
        val sampleObject = json.getModelFromJSON<TestModel>(typeToken)
        assertEquals("test", sampleObject.property)
    }

    @Test
    fun testJsonArrayToHashMap() {
        val jsonArray = JSONArray("[{\"key1\":\"value1\"},{\"key2\":\"value2\"}]")
        val hashMap = jsonArray.jsonArrayToHashMap()
        assertEquals(2, hashMap.size)
        assertEquals("value1", hashMap["key1"])
        assertEquals("value2", hashMap["key2"])
    }

    // You can add more tests based on your specific use cases

    private data class TestModel(val property: String)
}