package net.soft.features.encryption.domain.repository.keyStore

import net.soft.features.encryption.data.models.enums.KeyPurpose
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