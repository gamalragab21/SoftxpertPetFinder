package net.xpert.features.getPetByCategoryUC.data.models

import net.xpert.core.common.data.consts.Constants
import net.xpert.core.common.domain.model.request.IRequestValidation
import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.core.common.domain.model.request.RequestContractType
import net.xpert.features.getPetTypes.domain.enums.PetType

data class CategoryPetRequest(val petType: PetType, val currentPage: Int = 1) : IRequestValidation {
    override fun isInitialState(): Boolean = petType.type.isEmpty()

    override val remoteMap: RemoteRequest
        get() = RemoteRequest(
            requestQueries = hashMapOf<String, Any>(
                Constants.CURRENT_PAGE to currentPage,
                Constants.COUNT_PER_PAGE to Constants.PAGE_SIZE
            ).apply {
                if (addingTypeInQuery()) put(Constants.ANIMAL_TYPE, petType.type)
            }
        )

    private fun addingTypeInQuery() = petType != PetType.ALL

    override fun getRequestContracts(): HashMap<RequestContractType, HashMap<String, Boolean>> {
        return hashMapOf(
            RequestContractType.QUERIES to hashMapOf(
                Constants.ANIMAL_TYPE to addingTypeInQuery(),
                Constants.CURRENT_PAGE to true,
                Constants.COUNT_PER_PAGE to true
            )
        )
    }

}