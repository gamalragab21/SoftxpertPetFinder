package net.soft.features.getUserTokenUC.data.models

import net.soft.android.helpers.properties.domain.IConfigurationUtil
import net.soft.core.common.data.consts.Constants
import net.soft.core.common.domain.model.request.IRequestValidation
import net.soft.core.common.domain.model.request.RemoteRequest
import net.soft.core.common.domain.model.request.RequestContractType

data class TokenRequest(
    private val clientID: String, private val secretId: String
) : IRequestValidation {
    override fun isInitialState(): Boolean = clientID.isEmpty() || secretId.isEmpty()

    override val remoteMap: RemoteRequest
        get() = RemoteRequest(
            requestBody = hashMapOf(
                Constants.CLIENT_ID to clientID,
                Constants.CLIENT_SECRET to secretId,
                Constants.GRANT_TYPE to Constants.CLIENT_CREDENTIALS
            )
        )

    override fun getRequestContracts(): HashMap<RequestContractType, HashMap<String, Boolean>> {
        return hashMapOf(
            RequestContractType.BODY to hashMapOf(
                Constants.CLIENT_ID to true,
                Constants.CLIENT_SECRET to true,
                Constants.GRANT_TYPE to true
            )
        )
    }

    companion object {
        fun buildTokenRequestFromAssets(configurationUtil: IConfigurationUtil): TokenRequest {
            return TokenRequest(
                configurationUtil.getApiKey(), configurationUtil.getSecretKey(),
            )
        }
    }
}