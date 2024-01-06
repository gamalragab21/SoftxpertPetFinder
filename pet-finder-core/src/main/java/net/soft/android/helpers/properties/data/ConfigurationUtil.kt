package net.soft.android.helpers.properties.data

import android.content.Context
import net.soft.android.helpers.properties.domain.ConfigurationKey
import net.soft.android.helpers.properties.domain.IConfigurationUtil
import net.soft.core.R
import java.io.IOException
import java.util.Properties

internal class ConfigurationUtil(private val context: Context, private val fileName: String) :
    IConfigurationUtil {

    private var properties = Properties()

    override fun getApiKey(): String {
        return getProperty(ConfigurationKey.API_KEY)
    }

    override fun getSecretKey(): String {
        return getProperty(ConfigurationKey.SECRET_KEY)
    }

    override fun getServerBaseUrl(): String {
        return getProperty(ConfigurationKey.SERVER_BASE_URL)
    }

    override fun getProperty(key: ConfigurationKey): String {
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