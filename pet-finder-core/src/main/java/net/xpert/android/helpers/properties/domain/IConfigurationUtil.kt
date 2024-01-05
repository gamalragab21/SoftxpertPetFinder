package net.xpert.android.helpers.properties.domain

interface IConfigurationUtil {
    fun getSecretKey(): String
    fun getServerBaseUrl(): String
    fun getApiKey(): String
    fun getProperty(key: ConfigurationKey): String
}