package net.soft.features.getUserTokenUC.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.soft.android.helpers.properties.domain.IConfigurationUtil
import net.soft.core.common.domain.repository.local.keyValue.dataStore.IDataStoreStorageFile
import net.soft.core.common.domain.repository.remote.INetworkProvider
import net.soft.features.encryption.domain.repository.IEncryptionHelper
import net.soft.features.getUserTokenUC.data.repository.GetUserTokenRepository
import net.soft.features.getUserTokenUC.data.repository.local.GetUserTokenLocalDs
import net.soft.features.getUserTokenUC.data.repository.remote.GetUserTokenRemoteDs
import net.soft.features.getUserTokenUC.domain.repository.IGetUserTokenRepository
import net.soft.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs
import net.soft.features.getUserTokenUC.domain.repository.remote.IGetUserTokenRemoteDs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UserTokenDiModule {

    @Provides
    @Singleton
    fun provideUserTokenRemoteDS(provider: INetworkProvider): IGetUserTokenRemoteDs =
        GetUserTokenRemoteDs(provider)

    @Provides
    @Singleton
    fun provideUserTokenLocalDs(
        dataStoreStorageFile: IDataStoreStorageFile,
        encryptionHelper: IEncryptionHelper, configurationUtil: IConfigurationUtil
    ): IGetUserTokenLocalDs =
        GetUserTokenLocalDs(dataStoreStorageFile, encryptionHelper, configurationUtil)

    @Provides
    @Singleton
    fun provideUserTokenRepository(
        localDs: IGetUserTokenLocalDs, remoteDs: IGetUserTokenRemoteDs
    ): IGetUserTokenRepository = GetUserTokenRepository(localDs, remoteDs)
}