package net.soft.features.getUserTokenUC.data.repository.remote

import net.soft.core.common.domain.model.request.RemoteRequest
import net.soft.core.common.domain.repository.remote.INetworkProvider
import net.soft.features.getUserTokenUC.data.models.dto.TokenDto
import net.soft.features.getUserTokenUC.domain.repository.remote.IGetUserTokenRemoteDs

internal class GetUserTokenRemoteDs(
    private val provider: INetworkProvider
) : IGetUserTokenRemoteDs {
    override suspend fun generateUserToke(remoteRequest: RemoteRequest): TokenDto {
        return provider.post(
            TokenDto::class.java,
            pathUrl = TOKEN,
            requestBody = remoteRequest.requestBody
        )
    }

    companion object {
        private const val TOKEN = "oauth2/token"
    }
}