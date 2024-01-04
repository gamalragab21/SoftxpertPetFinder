package net.xpert.features.getUserTokenUC.domain.repository.remote

import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.features.getUserTokenUC.data.models.dto.TokenDto

internal interface IGetUserTokenRemoteDs {
    suspend fun generateUserToke(remoteRequest: RemoteRequest):TokenDto
}