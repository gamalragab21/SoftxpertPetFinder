package net.soft.features.encryption.domain.repository

import javax.crypto.SecretKey

internal interface IEncryptionHelper {

    // -------------------------------------------- AES --------------------------------------------

    fun getOrCreateSecretKey(keyAlias: String): SecretKey
    fun getOrCreateSecretKey(keyMaterial: ByteArray): SecretKey

    fun encryptAES(keyAlias: String, textInBytes: ByteArray): ByteArray
    fun encryptAES(secretKey: SecretKey, textInBytes: ByteArray): ByteArray
    fun decryptAES(keyAlias: String, encryptedDataWithIV: ByteArray): ByteArray
    fun decryptAES(secretKey: SecretKey, encryptedDataWithIV: ByteArray): ByteArray

    // -------------------------------------------- Common -----------------------------------------

    fun removeCryptographicKey(keyAlias: String)
}