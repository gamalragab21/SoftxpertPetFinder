package net.xpert.features.encryption.data.repository.keyStore

import android.security.keystore.KeyProperties
import net.xpert.android.helpers.logging.getClassLogger
import net.xpert.features.encryption.domain.repository.keyStore.IKeyStoreHelper
import net.xpert.features.encryption.domain.repository.keyStore.IKeyStoreHelper.Companion.PROVIDER
import net.xpert.features.encryption.data.models.enums.KeyPurpose
import java.security.KeyStore

internal open class KeyStoreHelper : IKeyStoreHelper {

    override val keyStore: KeyStore
        get() = KeyStore.getInstance(PROVIDER).apply {
            load(null)
        }

    override fun checkIfCryptographicKeyExist(keyAlias: String): Boolean {
        return keyStore.containsAlias(keyAlias)
    }

    override fun deleteCryptographicKey(keyAlias: String) {
        if (keyStore.containsAlias(keyAlias)) keyStore.deleteEntry(keyAlias)
        else logger.warning("There is no cryptographic key for this key alias: $keyAlias")
    }

    override fun getKeyPurpose(keyPurpose: KeyPurpose): Int = when (keyPurpose) {
        KeyPurpose.CRYPTOGRAPHIC -> KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        KeyPurpose.SIGNATURE -> KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        KeyPurpose.ALL -> KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT or
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
    }

    companion object {
        private val logger = getClassLogger()
    }
}