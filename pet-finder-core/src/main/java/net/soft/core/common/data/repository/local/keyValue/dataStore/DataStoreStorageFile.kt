package net.soft.core.common.data.repository.local.keyValue.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import net.soft.core.common.domain.repository.local.keyValue.IStorageKeyValue
import net.soft.core.common.domain.repository.local.keyValue.dataStore.IDataStoreStorageFile
import java.io.File

internal class DataStoreStorageFile(private val context: Context) : IDataStoreStorageFile {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_FILE_NAME)

    override val storageKV: IStorageKeyValue by lazy {
        DataStoreStorageKV(dataStore = context.dataStore)
    }

    override suspend fun clearStorageFile() {
        context.dataStore.edit { it.clear() }
    }

    override suspend fun deleteStorageFile(): Boolean {
        val file = File(context.filesDir, "/datastore/$DATA_STORE_FILE_NAME.preferences_pb")
        return file.delete()
    }

    companion object {
        private const val DATA_STORE_FILE_NAME = "pet_finder_user_token"
    }
}