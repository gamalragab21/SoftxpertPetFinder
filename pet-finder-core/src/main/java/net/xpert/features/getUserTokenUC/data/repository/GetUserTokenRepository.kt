package net.xpert.features.getUserTokenUC.data.repository

import net.xpert.features.getUserTokenUC.data.mapper.UserTokenMapper
import net.xpert.features.getUserTokenUC.data.models.TokenRequest
import net.xpert.features.getUserTokenUC.domain.models.Token
import net.xpert.features.getUserTokenUC.domain.repository.IGetUserTokenRepository
import net.xpert.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs
import net.xpert.features.getUserTokenUC.domain.repository.remote.IGetUserTokenRemoteDs

internal class GetUserTokenRepository(
    private val localDs: IGetUserTokenLocalDs, private val remoteDs: IGetUserTokenRemoteDs
) : IGetUserTokenRepository {
    override suspend fun saveUserToken(token: Token) {
        val result = UserTokenMapper.domainToEntity(token)
        localDs.saveUserToken(result)
    }

    override suspend fun getUserTokenFromLocal(): Token {
        val result = localDs.getUserToken()
        return UserTokenMapper.entityToDomain(result)
    }

    override suspend fun getUserTokenFromRemote(body: TokenRequest): Token {
        val result = remoteDs.generateUserToke(body.remoteMap)
        return UserTokenMapper.dtoToDomain(result)
    }
}