package net.xpert.features.encryption.domain.repository.keyStore

import net.xpert.features.encryption.data.models.enums.KeyPurpose
import java.security.KeyStore

internal interface IKeyStoreHelper {

    val keyStore: KeyStore

    fun checkIfCryptographicKeyExist(keyAlias: String): Boolean
    fun deleteCryptographicKey(keyAlias: String)
    fun getKeyPurpose(keyPurpose: KeyPurpose): Int

    companion object {
        const val PROVIDER = "AndroidKeyStore"
    }
}