package net.xpert.features.getUserTokenUC.data.repository.local

import net.xpert.android.extentions.base64Decode
import net.xpert.android.extentions.base64Encode
import net.xpert.android.extentions.getModelFromJSON
import net.xpert.android.extentions.toJson
import net.xpert.android.helpers.properties.domain.IConfigurationUtil
import net.xpert.core.common.data.repository.local.keyValue.StorageKeyEnum
import net.xpert.core.common.domain.repository.local.keyValue.dataStore.IDataStoreStorageFile
import net.xpert.features.encryption.domain.repository.IEncryptionHelper
import net.xpert.features.getUserTokenUC.data.models.entity.TokenEntity
import net.xpert.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs

internal class GetUserTokenLocalDs(
    private val dataStoreStorageFile: IDataStoreStorageFile,
    private val encryptionHelper: IEncryptionHelper,
    private val configurationUtil: IConfigurationUtil
) : IGetUserTokenLocalDs {

    override suspend fun getUserToken(): TokenEntity {
        val storedTokenJson =
            dataStoreStorageFile.storageKV.readEntry(StorageKeyEnum.USER_TOKEN, "")

        return if (storedTokenJson.isNotEmpty()) {
            storedTokenJson.getModelFromJSON<TokenEntity>(TokenEntity::class.java).apply {
                accessToken = decryptToken(accessToken)
            }
        } else TokenEntity()
    }

    override suspend fun saveUserToken(result: TokenEntity) {
        result.accessToken = encryptToken(result.accessToken)
        dataStoreStorageFile.storageKV.saveEntry(StorageKeyEnum.USER_TOKEN, result.toJson())
    }

    private fun encryptToken(accessToken: String): String {
        val keyAlias = getSecretKeyAsKeyAlias()

        return encryptionHelper.encryptAES(keyAlias, accessToken.encodeToByteArray())
            .base64Encode()
    }

    private fun decryptToken(accessToken: String): String {
        val keyAlias = getSecretKeyAsKeyAlias()

        return encryptionHelper.decryptAES(keyAlias, accessToken.base64Decode())
            .decodeToString()
    }

    private fun getSecretKeyAsKeyAlias() =
        configurationUtil.getApiKey()
}