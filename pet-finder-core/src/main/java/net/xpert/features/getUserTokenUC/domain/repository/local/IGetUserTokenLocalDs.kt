package net.xpert.features.getUserTokenUC.domain.repository.local

import net.xpert.features.getUserTokenUC.data.models.entity.TokenEntity

internal interface IGetUserTokenLocalDs {
    suspend fun isFirstTime(): Boolean
    suspend fun setISFirstTime(isFirstTime: Boolean = true)
    suspend fun getUserToke(): TokenEntity
    suspend fun saveUserToken(result: TokenEntity)
}