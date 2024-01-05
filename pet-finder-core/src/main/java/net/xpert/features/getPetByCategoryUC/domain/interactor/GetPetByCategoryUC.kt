package net.xpert.features.getPetByCategoryUC.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.xpert.core.common.data.model.exception.LeonException
import net.xpert.core.common.domain.interactor.UseCaseLocalThenRemoteThenCache
import net.xpert.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.xpert.features.getPetByCategoryUC.domain.models.Pet
import net.xpert.features.getPetByCategoryUC.domain.repository.IGetPetRepository
import javax.inject.Inject

class GetPetByCategoryUC @Inject constructor(private val repository: IGetPetRepository) :
    UseCaseLocalThenRemoteThenCache<Pet, CategoryPetRequest>() {
    override fun executeLocalDS(body: CategoryPetRequest?): Flow<Pet> = flow {
        if (body == null || body.isInitialState())
            throw LeonException.Local.RequestValidation(CategoryPetRequest::class)
        body.validateRequestContract().also {
            emit(repository.getPetsByCategoryNameFromLocal(body.petType, body.currentPage))
        }
    }

    override fun performRemoteOperation(domain: Pet?): Boolean = domain?.animals?.isEmpty() == true

    override fun executeRemoteDS(body: CategoryPetRequest?): Flow<Pet> = flow {
        emit(repository.getPetsByCategoryNameFromRemote(body!!.remoteMap))
    }

    override fun performLocalOperation(domain: Pet): Boolean = domain.animals.isNotEmpty()

    override suspend fun executeLocalOperation(domain: Pet, body: CategoryPetRequest?) {
        repository.saveAnimals(domain.animals)
    }
}