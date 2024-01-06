package net.soft.features.encryption.domain.repository.keyStore.aes

import net.soft.features.encryption.domain.repository.keyStore.IKeyStoreHelper
import javax.crypto.SecretKey

internal interface IKeyStoreAESHelper : IKeyStoreHelper {

    val transformation: String

    fun getOrCreateSecretKey(keyAlias: String): SecretKey
}