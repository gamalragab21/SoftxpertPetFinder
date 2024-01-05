package net.soft.features.getPetByCategoryUC.domain.repository.local

import net.soft.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.soft.features.getPetTypes.domain.enums.PetType

interface IGetPetByCategoryLocalDs {
    suspend fun insertAnimals(animals: List<AnimalEntity>)
    suspend fun getAnimals(petType: PetType, currentPage: Int): List<AnimalEntity>
    suspend fun getTotalItems(): Int
}