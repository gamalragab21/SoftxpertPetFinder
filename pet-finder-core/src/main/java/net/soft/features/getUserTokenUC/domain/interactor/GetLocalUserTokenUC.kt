package net.soft.features.getUserTokenUC.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.soft.core.common.domain.interactor.UseCaseLocal
import net.soft.features.getUserTokenUC.data.mapper.UserTokenMapper
import net.soft.features.getUserTokenUC.domain.models.Token
import net.soft.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs
import javax.inject.Inject

internal class GetLocalUserTokenUC @Inject constructor(private val localDs: IGetUserTokenLocalDs) :
    UseCaseLocal<Token, Unit>() {
    override fun executeLocalDS(body: Unit?): Flow<Token> = flow {
        val result = localDs.getUserToken()
        emit(UserTokenMapper.entityToDomain(result))
    }
}