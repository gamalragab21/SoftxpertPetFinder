package net.xpert.features.getPetByCategoryUC.domain.repository

import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.features.getPetByCategoryUC.domain.models.Animal
import net.xpert.features.getPetTypes.domain.enums.PetType

interface IGetPetRepository {
    suspend fun getPetsByCategoryNameFromRemote(remoteRequest: RemoteRequest): List<Animal>
    suspend fun getPetsByCategoryNameFromLocal(petType: PetType): List<Animal>
    suspend fun saveAnimals(animals: List<Animal>)
}