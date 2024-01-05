package net.soft.features.getPetByCategoryUC.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.soft.core.common.data.repository.local.room.PetFinderDatabase
import net.soft.core.common.domain.repository.remote.INetworkProvider
import net.soft.features.getPetByCategoryUC.data.repository.GetPetRepository
import net.soft.features.getPetByCategoryUC.data.repository.local.GetPetByCategoryLocalDs
import net.soft.features.getPetByCategoryUC.data.repository.remote.GetPetByCategoryRemoteDs
import net.soft.features.getPetByCategoryUC.domain.repository.IGetPetRepository
import net.soft.features.getPetByCategoryUC.domain.repository.local.IGetPetByCategoryLocalDs
import net.soft.features.getPetByCategoryUC.domain.repository.remote.IGetPetByCategoryRemoteDs
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