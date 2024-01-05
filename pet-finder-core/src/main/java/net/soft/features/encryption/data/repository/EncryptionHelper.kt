package net.soft.features.encryption.data.repository

import android.security.keystore.KeyProperties
import net.soft.core.common.data.model.exception.LeonException
import net.soft.features.encryption.domain.repository.IEncryptionHelper
import net.soft.features.encryption.domain.repository.keyStore.aes.IKeyStoreAESHelper
import net.soft.core.R
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

internal class EncryptionHelper(private val keyStoreAES: IKeyStoreAESHelper) : IEncryptionHelper {

    // ------------------------------------- AES ---------------------------------------------------

    override fun getOrCreateSecretKey(keyAlias: String): SecretKey {
        return keyStoreAES.getOrCreateSecretKey(keyAlias)
    }

    override fun getOrCreateSecretKey(keyMaterial: ByteArray): SecretKey {
        return SecretKeySpec(keyMaterial, KeyProperties.KEY_ALGORITHM_AES)
    }

    /**
     * Encrypts sensitive data using a secret key obtained from the Android KeyStore.
     *
     * @param keyAlias     The key alias used to obtain the cryptographic secret key.
     * @param textInBytes  The data to be encrypted.
     * @return             The encrypted byte array.
     */
    override fun encryptAES(keyAlias: String, textInBytes: ByteArray): ByteArray {
        return encryptAES(getOrCreateSecretKey(keyAlias), textInBytes)
    }

    override fun encryptAES(secretKey: SecretKey, textInBytes: ByteArray): ByteArray {
        return try {
            val iv = generateRandomIV()
            val cipher = Cipher.getInstance(keyStoreAES.transformation).apply {
                init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            }
            val encryptedData = cipher.doFinal(textInBytes)

            // Prepend IV to the encrypted data.
            val encryptedDataWithIV = ByteArray(iv.size + encryptedData.size)
            System.arraycopy(iv, 0, encryptedDataWithIV, 0, iv.size)
            System.arraycopy(encryptedData, 0, encryptedDataWithIV, iv.size, encryptedData.size)
            encryptedDataWithIV
        } catch (e: Exception) {
            e.printStackTrace()
            throw LeonException.Local.IOOperation(
                messageRes = R.string.unexpected_error_occurred_while_encrypting,
                message = e.message
            )
        }
    }

    override fun decryptAES(keyAlias: String, encryptedDataWithIV: ByteArray): ByteArray {
        return decryptAES(getOrCreateSecretKey(keyAlias), encryptedDataWithIV)
    }

    override fun decryptAES(secretKey: SecretKey, encryptedDataWithIV: ByteArray): ByteArray {
        return try {
            val iv = ByteArray(IV_LENGTH)
            val encryptedData = ByteArray(encryptedDataWithIV.size - IV_LENGTH)
            System.arraycopy(encryptedDataWithIV, 0, iv, 0, IV_LENGTH)
            System.arraycopy(encryptedDataWithIV, IV_LENGTH, encryptedData, 0, encryptedData.size)

            val cipher = Cipher.getInstance(keyStoreAES.transformation).apply {
                init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
            }
            cipher.doFinal(encryptedData)
        } catch (e: Exception) {
            e.printStackTrace()
            throw LeonException.Local.IOOperation(
                messageRes = R.string.unexpected_error_occurred_while_decrypting,
                message = e.message
            )
        }
    }

    private fun generateRandomIV(): ByteArray {
        val iv = ByteArray(IV_LENGTH)
        val secureRandom = SecureRandom.getInstanceStrong()
        secureRandom.nextBytes(iv)
        return iv
    }

    // ------------------------------------- RSA ---------------------------------------------------

    override fun removeCryptographicKey(keyAlias: String) {
        keyStoreAES.deleteCryptographicKey(keyAlias)
    }

    companion object {
        private const val IV_LENGTH = 12 // GCM mode requires IV of 12 bytes.
    }
}