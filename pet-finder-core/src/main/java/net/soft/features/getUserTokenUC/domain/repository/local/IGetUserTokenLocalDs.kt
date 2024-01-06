package net.soft.features.getUserTokenUC.domain.repository.local

import net.soft.features.getUserTokenUC.data.models.entity.TokenEntity

internal interface IGetUserTokenLocalDs {
    suspend fun getUserToken(): TokenEntity
    suspend fun saveUserToken(result: TokenEntity)
}