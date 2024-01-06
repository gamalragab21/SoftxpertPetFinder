package net.soft.core.common.data.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import net.soft.core.common.domain.repository.local.room.PetFinderDao
import net.soft.features.getPetByCategoryUC.data.models.entity.AnimalEntity

@Database(entities = [AnimalEntity::class], version = 1)
internal abstract class PetFinderDatabase : RoomDatabase() {
    abstract fun petFinderDao(): PetFinderDao
}
