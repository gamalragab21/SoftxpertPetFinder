package net.xpert.features.getPetByCategoryUC.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.xpert.core.common.data.model.Resource
import net.xpert.core.common.data.model.exception.LeonException
import net.xpert.core.common.domain.interactor.UseCaseLocalThenRemoteThenCache
import net.xpert.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.xpert.features.getPetByCategoryUC.domain.models.Animal
import net.xpert.features.getPetByCategoryUC.domain.repository.IGetPetRepository
import javax.inject.Inject

class GetPetByCategoryUC @Inject constructor(private val repository: IGetPetRepository) :
    UseCaseLocalThenRemoteThenCache<List<Animal>, CategoryPetRequest>() {
    override fun executeLocalDS(body: CategoryPetRequest?): Flow<List<Animal>> = flow {
        if (body == null || body.isInitialState())
            throw LeonException.Local.RequestValidation(CategoryPetRequest::class)
        body.validateRequestContract().also {
            emit(repository.getPetsByCategoryNameFromLocal(body.petType))
        }
    }

    override fun performRemoteOperation(domain: List<Animal>?): Boolean = domain?.isEmpty() == true

    override fun executeRemoteDS(body: CategoryPetRequest?): Flow<List<Animal>> = flow {
        emit(repository.getPetsByCategoryNameFromRemote(body!!.remoteMap))
    }

    override fun performLocalOperation(domain: List<Animal>): Boolean = domain.isNotEmpty()

    override suspend fun executeLocalOperation(domain: List<Animal>, body: CategoryPetRequest?) {
        repository.saveAnimals(domain)
    }

    override suspend fun validateFailureResponse(
        exception: LeonException,
        body: CategoryPetRequest?
    ): Resource<List<Animal>>? {
        return super.validateFailureResponse(exception, body)
    }
}