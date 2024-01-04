package net.xpert.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.xpert.core.common.data.consts.Constants
import net.xpert.core.common.data.repository.local.keyValue.dataStore.DataStoreStorageFile
import net.xpert.core.common.data.repository.local.room.PetFinderDatabase
import net.xpert.core.common.domain.repository.local.keyValue.dataStore.IDataStoreStorageFile
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object StorageModule {


    @Provides
    @Singleton
    fun providePetFinderDatabase(@ApplicationContext context: Context): PetFinderDatabase =
        Room.databaseBuilder(context, PetFinderDatabase::class.java, Constants.SOFTXPERT_DB_NAME)
            .build()

    @Provides
    @Singleton
    fun providePetDataStoreStorageFile(@ApplicationContext context: Context):IDataStoreStorageFile = DataStoreStorageFile(context)
}