package net.soft.features.encryption.data.repository.keyStore.aes

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import net.soft.android.helpers.logging.getClassLogger
import net.soft.core.common.data.model.exception.LeonException
import net.soft.features.encryption.data.models.enums.KeyPurpose
import net.soft.features.encryption.data.repository.keyStore.KeyStoreHelper
import net.soft.features.encryption.domain.repository.keyStore.IKeyStoreHelper.Companion.PROVIDER
import net.soft.features.encryption.domain.repository.keyStore.aes.IKeyStoreAESHelper
import net.soft.core.R
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal object KeyStoreAESHelper : IKeyStoreAESHelper, KeyStoreHelper() {

    override val transformation: String
        get() = TRANSFORMATION

    override fun getOrCreateSecretKey(keyAlias: String): SecretKey {
        val secretKeyEntry = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
        return if (secretKeyEntry != null) {
            logger.info("SecretKey already exists, retrieving it from the keystore.")
            secretKeyEntry.secretKey
        } else {
            logger.info("Generating a new SecretKey.")
            generateSecretKey(keyAlias)
        }
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        if (keyAlias.isEmpty())
            throw LeonException.Local.IOOperation(R.string.failed_to_keystore_alias_empty)
        return KeyGenerator.getInstance(ALGORITHM, PROVIDER)
            .apply { init(getKeyGen(keyAlias)) }
            .generateKey()
    }

    private fun getKeyGen(keyAlias: String) = KeyGenParameterSpec.Builder(
        keyAlias, getKeyPurpose(KeyPurpose.CRYPTOGRAPHIC)
    ).apply {
        setKeySize(KEY_SIZE)
        setBlockModes(BLOCK_MODE)
        setEncryptionPaddings(PADDING)
        // This is required to be able to provide the IV ourselves
        setRandomizedEncryptionRequired(false)
    }.build()

    private const val KEY_SIZE = 256
    private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
    private const val PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
    private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

    private val logger = getClassLogger()
}