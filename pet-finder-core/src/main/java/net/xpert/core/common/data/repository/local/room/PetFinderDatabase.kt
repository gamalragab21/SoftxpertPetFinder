package net.xpert.core.common.data.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import net.xpert.core.common.domain.repository.local.room.PetFinderDao
import net.xpert.features.token.Token

@Database(entities = [Token::class], version = 1)
internal abstract class PetFinderDatabase : RoomDatabase() {
    abstract fun petFinderDao(): PetFinderDao
}
