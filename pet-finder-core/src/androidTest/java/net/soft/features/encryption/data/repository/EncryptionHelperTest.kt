package net.soft.features.encryption.data.repository

import net.soft.core.common.data.model.exception.LeonException
import net.soft.features.encryption.data.repository.keyStore.aes.KeyStoreAESHelper
import net.soft.features.encryption.domain.repository.IEncryptionHelper
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Arrays

@RunWith(JUnit4::class)
class EncryptionHelperTest {

    private val keyAliasAES = "test_key_alias_aes"
    private lateinit var encryptionHelper: IEncryptionHelper

    @Before
    fun setUp() {
        encryptionHelper = EncryptionHelper(KeyStoreAESHelper)
    }

    // ------------------------------------- AES Encryption and Decryption -------------------------------------

    @Test
    fun testAesEncryptionAndDecryption() {
        val inputText = "Hello, AES!".toByteArray()
        val encryptedDataWithIV = encryptionHelper.encryptAES(keyAliasAES, inputText)
        val decryptedData = encryptionHelper.decryptAES(keyAliasAES, encryptedDataWithIV)

        assertArrayEquals(inputText, decryptedData)
    }

    @Test
    fun testAesEncryptionAndDecryptionWithEmptyInput() {
        val inputText = ByteArray(0)
        val encryptedDataWithIV = encryptionHelper.encryptAES(keyAliasAES, inputText)
        val decryptedData = encryptionHelper.decryptAES(keyAliasAES, encryptedDataWithIV)

        assertArrayEquals(inputText, decryptedData)
    }

    @Test
    fun testAesEncryptionWithOneByteInput() {
        val inputText = byteArrayOf(42)
        val encryptedDataWithIV = encryptionHelper.encryptAES(keyAliasAES, inputText)
        val decryptedData = encryptionHelper.decryptAES(keyAliasAES, encryptedDataWithIV)

        assertArrayEquals(inputText, decryptedData)
    }

    @Test
    fun testAesDecryptionWithIncorrectKey() {
        val inputText = "Hello, AES!".toByteArray()
        val encryptedDataWithIV = encryptionHelper.encryptAES(keyAliasAES, inputText)

        // Try to decrypt with a different key alias
        val keyAliasAnother = "another_key_alias"
        try {
            encryptionHelper.decryptAES(keyAliasAnother, encryptedDataWithIV)
            fail("Expected exception not thrown.")
        } catch (e: Exception) {
            assertTrue(e is LeonException.Local.IOOperation)
        }
    }

    // ------------------------------------- AES Key Generation (KeyStoreAESHelper) -------------------------------------

    @Test
    fun testAesKeyRetrievalWithExistingKey() {
        // Ensure that the key is already present in the KeyStore for this test case.
        val key = KeyStoreAESHelper.getOrCreateSecretKey(keyAliasAES)
        val retrievedKey = KeyStoreAESHelper.getOrCreateSecretKey(keyAliasAES)

        assertTrue(Arrays.equals(key.encoded, retrievedKey.encoded))
    }
}
