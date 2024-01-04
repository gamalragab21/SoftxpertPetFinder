package net.xpert.core.common.data.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import net.xpert.core.common.domain.repository.local.room.PetFinderDao
import net.xpert.features.getPetByCategoryUC.data.models.entity.AnimalEntity

@Database(entities = [AnimalEntity::class], version = 1)
internal abstract class PetFinderDatabase : RoomDatabase() {
    abstract fun petFinderDao(): PetFinderDao
}
