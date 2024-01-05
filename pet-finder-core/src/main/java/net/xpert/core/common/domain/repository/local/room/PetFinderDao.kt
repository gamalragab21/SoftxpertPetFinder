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

    @Query("SELECT * FROM pet_table ORDER BY id ASC LIMIT 20 OFFSET :offset")
    suspend fun getAllAnimals(offset: Int): List<AnimalEntity>

    @Query("SELECT * FROM pet_table where type=:type ORDER BY id ASC LIMIT 20 OFFSET :offset")
    suspend fun getAnimalsByCategory(type: String, offset: Int): List<AnimalEntity>

    @Query("SELECT COUNT(*) FROM pet_table")
    fun getTotalItemCount(): Int
}