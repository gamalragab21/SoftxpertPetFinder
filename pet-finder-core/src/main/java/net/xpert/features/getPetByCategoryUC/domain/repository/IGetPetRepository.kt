package net.xpert.features.getPetByCategoryUC.domain.repository

import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.features.getPetByCategoryUC.domain.models.Animal
import net.xpert.features.getPetByCategoryUC.domain.models.Pet
import net.xpert.features.getPetTypes.domain.enums.PetType

interface IGetPetRepository {
    suspend fun getPetsByCategoryNameFromRemote(remoteRequest: RemoteRequest): Pet
    suspend fun getPetsByCategoryNameFromLocal(petType: PetType,currentPage:Int): Pet
    suspend fun saveAnimals(animals: List<Animal>)
}