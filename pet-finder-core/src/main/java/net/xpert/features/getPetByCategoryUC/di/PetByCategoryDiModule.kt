package net.xpert.features.getPetByCategoryUC.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.xpert.core.common.data.repository.local.room.PetFinderDatabase
import net.xpert.core.common.domain.repository.remote.INetworkProvider
import net.xpert.features.getPetByCategoryUC.data.repository.GetPetRepository
import net.xpert.features.getPetByCategoryUC.data.repository.local.GetPetByCategoryLocalDs
import net.xpert.features.getPetByCategoryUC.data.repository.remote.GetPetByCategoryRemoteDs
import net.xpert.features.getPetByCategoryUC.domain.repository.IGetPetRepository
import net.xpert.features.getPetByCategoryUC.domain.repository.local.IGetPetByCategoryLocalDs
import net.xpert.features.getPetByCategoryUC.domain.repository.remote.IGetPetByCategoryRemoteDs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PetByCategoryDiModule {

    @Provides
    @Singleton
    fun provideGetPetByCategoryRemoteDs(provider: INetworkProvider): IGetPetByCategoryRemoteDs =
        GetPetByCategoryRemoteDs(provider)

    @Provides
    @Singleton
    fun provideGetPetByCategoryLocalDs(petFinderDatabase: PetFinderDatabase): IGetPetByCategoryLocalDs =
        GetPetByCategoryLocalDs(petFinderDatabase.petFinderDao())

    @Provides
    @Singleton
    fun provideGetPetRepository(
        localDs: IGetPetByCategoryLocalDs, remoteDs: IGetPetByCategoryRemoteDs
    ): IGetPetRepository = GetPetRepository(localDs, remoteDs)
}