package net.soft.core.common.data.repository.local.keyValue.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import net.soft.android.extentions.base64Decode
import net.soft.android.extentions.base64Encode
import net.soft.core.common.data.model.exception.LeonException
import net.soft.core.common.domain.repository.local.keyValue.IStorageKeyEnum
import net.soft.core.common.domain.repository.local.keyValue.IStorageKeyValue
import net.soft.core.R

internal class DataStoreStorageKV(private val dataStore: DataStore<Preferences>) :
    IStorageKeyValue {

    override suspend fun <T> saveEntry(key: IStorageKeyEnum, data: T) {
        dataStore.edit {
            when (data) {
                is String -> it[stringPreferencesKey(key.keyValue)] = data
                is Int -> it[intPreferencesKey(key.keyValue)] = data
                is Boolean -> it[booleanPreferencesKey(key.keyValue)] = data
                is Float -> it[floatPreferencesKey(key.keyValue)] = data
                is Long -> it[longPreferencesKey(key.keyValue)] = data
                is ByteArray -> it[stringPreferencesKey(key.keyValue)] = data.base64Encode()
                else -> throw LeonException.Local.IOOperation(R.string.unsupported_data_type)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> readEntry(key: IStorageKeyEnum, defaultValue: T): T {
        return when (defaultValue) {
            is String -> (dataStore.data.map { it[stringPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as T

            is Int -> (dataStore.data.map { it[intPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as T

            is Boolean -> (dataStore.data.map { it[booleanPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as T

            is Float -> (dataStore.data.map { it[floatPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as T

            is Long -> (dataStore.data.map { it[longPreferencesKey(key.keyValue)] }
                .firstOrNull() ?: defaultValue) as T

            is ByteArray -> (dataStore.data.map { it[stringPreferencesKey(key.keyValue)] }
                .firstOrNull()?.base64Decode() ?: defaultValue) as T

            else -> throw LeonException.Local.IOOperation(R.string.unsupported_data_type)
        }
    }

    override suspend fun <T> clearEntry(key: IStorageKeyEnum, defaultValue: T) {
        dataStore.edit {
            when (defaultValue) {
                is String -> it[stringPreferencesKey(key.keyValue)] = defaultValue
                is Int -> it[intPreferencesKey(key.keyValue)] = defaultValue
                is Boolean -> it[booleanPreferencesKey(key.keyValue)] = defaultValue
                is Float -> it[floatPreferencesKey(key.keyValue)] = defaultValue
                is Long -> it[longPreferencesKey(key.keyValue)] = defaultValue
                is ByteArray -> it[stringPreferencesKey(key.keyValue)] = defaultValue.base64Encode()
                else -> throw LeonException.Local.IOOperation(R.string.unsupported_data_type)
            }
        }
    }

    override suspend fun hasEntry(key: IStorageKeyEnum): Boolean {
        var hasKey = false
        dataStore.edit { hasKey = it.contains(stringPreferencesKey(key.keyValue)) }
        return hasKey
    }
}