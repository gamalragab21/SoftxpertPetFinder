package net.soft.features.getPetByCategoryUC.domain.repository

import net.soft.core.common.domain.model.request.RemoteRequest
import net.soft.features.getPetByCategoryUC.domain.models.Animal
import net.soft.features.getPetByCategoryUC.domain.models.Pet
import net.soft.features.getPetTypes.domain.enums.PetType

interface IGetPetRepository {
    suspend fun getPetsByCategoryNameFromRemote(remoteRequest: RemoteRequest): Pet
    suspend fun getPetsByCategoryNameFromLocal(petType: PetType, currentPage: Int): Pet
    suspend fun saveAnimals(animals: List<Animal>)
}