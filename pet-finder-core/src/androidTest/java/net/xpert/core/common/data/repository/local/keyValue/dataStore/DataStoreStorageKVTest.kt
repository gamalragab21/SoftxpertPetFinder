package net.xpert.core.common.data.repository.local.keyValue.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import net.xpert.core.common.data.repository.local.keyValue.dataStore.StorageKeyValueTest.Companion.FILE_NAME
import net.xpert.core.common.domain.repository.local.keyValue.IStorageKeyEnum
import net.xpert.core.common.domain.repository.local.keyValue.IStorageKeyValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.File

private val Context.testStore by preferencesDataStore(FILE_NAME)

class StorageKeyValueTest {
    private val context = InstrumentationRegistry.getInstrumentation().context
    private lateinit var dataStoreStorage: IStorageKeyValue
    private val testKey = "my_test_key"

    @Mock
    private lateinit var storageKeyEnum: IStorageKeyEnum

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataStoreStorage = DataStoreStorageKV(dataStore = context.testStore)
        Mockito.`when`(storageKeyEnum.keyValue).thenReturn(testKey)
    }

    @After
    fun deleteFile() = runTest {
        context.testStore.edit { it.clear() }
        File(
            context.filesDir, "/datastore/$FILE_NAME.preferences_pb"
        ).delete()
    }

    @Test
    fun testGetStringWhenValueExists() = runTest {
        // Simulate the scenario where you have the value saved in your DataStore
        val expectedValue = "Android Developer!!"

        // When
        dataStoreStorage.saveEntry(storageKeyEnum, expectedValue)
        val result = dataStoreStorage.readEntry(storageKeyEnum, "")

        // Then
        assert(result == expectedValue)
    }

    @Test
    fun testGetStringWhenValueDoesNotExist() = runTest {
        // Simulate the scenario where the value doesn't exist in your DataStore

        // When
        val result = dataStoreStorage.readEntry(storageKeyEnum, "")

        // Then
        assert(result.isEmpty())
    }

    @Test
    fun testGetIntWhenValueExists() = runTest {
        val expectedValue = 2172001

        // When
        dataStoreStorage.saveEntry(storageKeyEnum, expectedValue)
        val result = dataStoreStorage.readEntry(storageKeyEnum, -1)

        // Then
        assert(result == expectedValue)
    }

    @Test
    fun testGetIntWhenValueDoesNotExist() = runTest {
        // When
        val result = dataStoreStorage.readEntry(storageKeyEnum, -1)

        // Then
        assert(result == -1)
    }

    @Test
    fun testGetBooleanWhenValueExists() = runTest {
        val expectedValue = true

        // When
        dataStoreStorage.saveEntry(storageKeyEnum, expectedValue)
        val result = dataStoreStorage.readEntry(storageKeyEnum, false)

        // Then
        assert(result == expectedValue)
    }

    @Test
    fun testGetBooleanWhenValueDoesNotExist() = runTest {
        // When
        val result = dataStoreStorage.readEntry(storageKeyEnum, false)

        // Then
        assert(!result)
    }

    @Test
    fun testCheckHasAKeyWhenValueDoesNotExist() = runTest {
        // When
        val result = dataStoreStorage.hasEntry(storageKeyEnum)
        println("testCheckHasAKeyWhenValueDoesNotExist:-> $result")
        // Then
        assert(!result)
    }

    @Test
    fun testCheckHasAKeyWhenValueDoesExist() = runTest {
        // When
        dataStoreStorage.saveEntry(storageKeyEnum, "Android Developer!")

        val result = dataStoreStorage.hasEntry(storageKeyEnum)
        // Then
        assert(result)
    }

    companion object {
        const val FILE_NAME = "test_data_store_file_name"
    }
}