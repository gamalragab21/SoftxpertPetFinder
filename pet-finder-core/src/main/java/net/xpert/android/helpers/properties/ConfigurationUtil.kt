package net.xpert.android.helpers.properties

import android.content.Context
import net.xpert.core.R
import java.io.IOException
import java.util.Properties

class ConfigurationUtil(private val context: Context, private val fileName: String) {

    private var properties = Properties()

    fun getProperty(key: ConfigurationKey): String {
        val keyValue = getPropertiesFile().getProperty(key.key, "")

        return keyValue.ifEmpty {
            throw NoSuchElementException(
                context.getString(R.string.property_key_is_empty_or_not_found, key.key)
            )
        }
    }

    private fun getPropertiesFile(): Properties {
        try {
            val inputStream = context.assets.open(fileName)
            properties.load(inputStream)
            return properties
        } catch (e: IOException) {
            throw IOException("\"${fileName}\" file is not found. Please add this file to assets folder in your project")
        }
    }
}