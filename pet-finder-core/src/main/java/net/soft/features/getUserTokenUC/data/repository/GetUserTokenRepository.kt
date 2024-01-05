package net.soft.features.getUserTokenUC.data.repository

import net.soft.features.getUserTokenUC.data.mapper.UserTokenMapper
import net.soft.features.getUserTokenUC.data.models.TokenRequest
import net.soft.features.getUserTokenUC.domain.models.Token
import net.soft.features.getUserTokenUC.domain.repository.IGetUserTokenRepository
import net.soft.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs
import net.soft.features.getUserTokenUC.domain.repository.remote.IGetUserTokenRemoteDs

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