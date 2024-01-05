package net.xpert.features.getUserTokenUC.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.xpert.core.common.domain.interactor.UseCaseLocal
import net.xpert.features.getUserTokenUC.data.mapper.UserTokenMapper
import net.xpert.features.getUserTokenUC.domain.models.Token
import net.xpert.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs
import javax.inject.Inject

internal class GetLocalUserTokenUC @Inject constructor(private val localDs: IGetUserTokenLocalDs) :
    UseCaseLocal<Token, Unit>() {
    override fun executeLocalDS(body: Unit?): Flow<Token> = flow {
        val result = localDs.getUserToken()
        emit(UserTokenMapper.entityToDomain(result))
    }
}