package net.xpert.features.getUserTokenUC.domain.repository

import net.xpert.features.getUserTokenUC.data.models.TokenRequest
import net.xpert.features.getUserTokenUC.domain.models.Token

interface IGetUserTokenRepository {

    suspend fun isFirstTime(): Token
    suspend fun setIsFirstTime(isFirstTime: Boolean = true)
    suspend fun saveUserToken(token: Token)
    suspend fun getUserTokenFromRemote(body: TokenRequest): Token
}