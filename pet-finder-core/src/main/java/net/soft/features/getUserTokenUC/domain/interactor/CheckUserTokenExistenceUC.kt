package net.soft.features.getUserTokenUC.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.soft.core.common.data.model.exception.LeonException
import net.soft.core.common.domain.interactor.UseCaseLocalThenRemoteThenCache
import net.soft.features.getUserTokenUC.data.models.TokenRequest
import net.soft.features.getUserTokenUC.domain.models.Token
import net.soft.features.getUserTokenUC.domain.repository.IGetUserTokenRepository
import javax.inject.Inject

class CheckUserTokenExistenceUC @Inject constructor(private val repository: IGetUserTokenRepository) :
    UseCaseLocalThenRemoteThenCache<Token, TokenRequest>() {
    override fun executeLocalDS(body: TokenRequest?): Flow<Token> = flow {
        emit(repository.getUserTokenFromLocal())
    }

    override fun performRemoteOperation(domain: Token?): Boolean =
        domain?.isInInitialState() == true

    override fun executeRemoteDS(body: TokenRequest?): Flow<Token> = flow {
        if (body == null || body.isInitialState())
            throw LeonException.Local.RequestValidation(TokenRequest::class)
        body.validateRequestContract().also {
            emit(repository.getUserTokenFromRemote(body))
        }
    }

    override fun performLocalOperation(domain: Token): Boolean = !domain.isInInitialState()

    override suspend fun executeLocalOperation(domain: Token, body: TokenRequest?) {
        repository.saveUserToken(domain)
    }
}