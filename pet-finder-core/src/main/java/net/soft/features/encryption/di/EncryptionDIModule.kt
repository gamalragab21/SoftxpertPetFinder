package net.soft.features.encryption.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.soft.features.encryption.data.repository.EncryptionHelper
import net.soft.features.encryption.data.repository.keyStore.aes.KeyStoreAESHelper
import net.soft.features.encryption.domain.repository.IEncryptionHelper
import net.soft.features.encryption.domain.repository.keyStore.aes.IKeyStoreAESHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object EncryptionDIModule {
    @Provides
    @Singleton
    fun provideKeyStoreAESHelper(): IKeyStoreAESHelper = KeyStoreAESHelper

    @Provides
    @Singleton
    fun provideEncryptionHelper(aesHelper: IKeyStoreAESHelper): IEncryptionHelper =
        EncryptionHelper(aesHelper)
}