package net.xpert.features.getPetByCategoryUC.data.models

import net.xpert.core.common.data.consts.Constants
import net.xpert.core.common.domain.model.request.IRequestValidation
import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.core.common.domain.model.request.RequestContractType
import net.xpert.features.getPetTypes.domain.enums.PetType

data class CategoryPetRequest(val petType: PetType) : IRequestValidation {
    override fun isInitialState(): Boolean = petType.type.isEmpty()

    override val remoteMap: RemoteRequest
        get() = RemoteRequest(
            requestQueries = if (addingTypeInQuery()) hashMapOf(
                Constants.ANIMAL_TYPE to petType.type
            ) else hashMapOf()
        )

    private fun addingTypeInQuery() = petType != PetType.ALL

    override fun getRequestContracts(): HashMap<RequestContractType, HashMap<String, Boolean>> {
        return hashMapOf(
            RequestContractType.QUERIES to hashMapOf(
                Constants.ANIMAL_TYPE to addingTypeInQuery()
            )
        )
    }

}