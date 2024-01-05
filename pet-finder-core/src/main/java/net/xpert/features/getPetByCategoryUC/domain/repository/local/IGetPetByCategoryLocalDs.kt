package net.xpert.features.getPetByCategoryUC.domain.repository.local

import net.xpert.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.xpert.features.getPetTypes.domain.enums.PetType

interface IGetPetByCategoryLocalDs {
    suspend fun insertAnimals(animals: List<AnimalEntity>)
    suspend fun getAnimals(petType: PetType, currentPage: Int): List<AnimalEntity>
    suspend fun getTotalItems(): Int
}