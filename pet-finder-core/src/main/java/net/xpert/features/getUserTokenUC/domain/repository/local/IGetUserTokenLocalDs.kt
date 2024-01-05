package net.xpert.features.getUserTokenUC.domain.repository.local

import net.xpert.features.getUserTokenUC.data.models.entity.TokenEntity

internal interface IGetUserTokenLocalDs {
    suspend fun getUserToken(): TokenEntity
    suspend fun saveUserToken(result: TokenEntity)
}