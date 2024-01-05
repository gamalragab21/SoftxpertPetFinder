package net.soft.features.getUserTokenUC.domain.repository.remote

import net.soft.core.common.domain.model.request.RemoteRequest
import net.soft.features.getUserTokenUC.data.models.dto.TokenDto

internal interface IGetUserTokenRemoteDs {
    suspend fun generateUserToke(remoteRequest: RemoteRequest): TokenDto
}