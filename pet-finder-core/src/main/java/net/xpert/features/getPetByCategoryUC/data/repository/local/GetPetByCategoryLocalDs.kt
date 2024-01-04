package net.xpert.features.getPetByCategoryUC.data.repository.local

import net.xpert.core.common.domain.repository.local.room.PetFinderDao
import net.xpert.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.xpert.features.getPetByCategoryUC.domain.repository.local.IGetPetByCategoryLocalDs
import net.xpert.features.getPetTypes.domain.enums.PetType
import net.xpert.features.getPetTypes.domain.enums.PetType.ALL
import javax.inject.Inject

internal class GetPetByCategoryLocalDs @Inject constructor(private val petFinderDao: PetFinderDao) :
    IGetPetByCategoryLocalDs {
    override suspend fun insertAnimals(animals: List<AnimalEntity>) {
        petFinderDao.insertAllAnimals(animals)
    }

    override suspend fun getAnimals(petType: PetType): List<AnimalEntity> {
        return when (petType) {
            ALL -> petFinderDao.getAllAnimals()
            else -> petFinderDao.getAnimalsByCategory(petType.type)
        }
    }
}