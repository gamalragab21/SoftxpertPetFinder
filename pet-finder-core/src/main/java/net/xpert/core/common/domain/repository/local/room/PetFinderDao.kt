package net.xpert.core.common.domain.repository.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.xpert.features.getPetByCategoryUC.data.models.entity.AnimalEntity

@Dao
internal interface PetFinderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAnimals(animals: List<AnimalEntity>)

    @Query("SELECT * FROM pet_table")
    suspend fun getAllAnimals(): List<AnimalEntity>
    @Query("SELECT * FROM pet_table where type=:type")
    suspend fun getAnimalsByCategory(type: String): List<AnimalEntity>
}