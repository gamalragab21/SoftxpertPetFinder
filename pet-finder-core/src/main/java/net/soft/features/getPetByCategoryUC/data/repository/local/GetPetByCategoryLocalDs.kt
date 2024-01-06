package net.soft.features.getPetByCategoryUC.data.repository.local

import net.soft.core.common.data.consts.Constants
import net.soft.core.common.domain.repository.local.room.PetFinderDao
import net.soft.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.soft.features.getPetByCategoryUC.domain.repository.local.IGetPetByCategoryLocalDs
import net.soft.features.getPetTypes.domain.enums.PetType
import net.soft.features.getPetTypes.domain.enums.PetType.ALL
import javax.inject.Inject

internal class GetPetByCategoryLocalDs @Inject constructor(private val petFinderDao: PetFinderDao) :
    IGetPetByCategoryLocalDs {
    /**
     *
     * In the context of Room and databases in general, the "OFFSET" clause is used to skip a specified number of rows from the beginning of the result set. It is often used in conjunction with the "LIMIT" clause for the purpose of pagination.
     *
     * Here's a brief explanation:
     *
     * LIMIT: Specifies the maximum number of rows to return in the result set.
     *
     * OFFSET: Specifies the number of rows to skip before starting to return rows from the result set.
     */
    override suspend fun insertAnimals(animals: List<AnimalEntity>) {
        petFinderDao.insertAllAnimals(animals)
    }

    override suspend fun getAnimals(petType: PetType, currentPage: Int): List<AnimalEntity> {
        val offset = Constants.PAGE_SIZE * (currentPage - 1)
        return when (petType) {
            ALL -> petFinderDao.getAllAnimals(offset)
            else -> petFinderDao.getAnimalsByCategory(petType.type, offset)
        }
    }

    override suspend fun getTotalItems(): Int = petFinderDao.getTotalItemCount()
}