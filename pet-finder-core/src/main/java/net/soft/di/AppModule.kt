package net.soft.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.soft.android.helpers.properties.data.ConfigurationUtil
import net.soft.android.helpers.properties.domain.IConfigurationUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConfigUtils(@ApplicationContext context: Context): IConfigurationUtil =
        ConfigurationUtil(context, "pet-finder-configuration.properties")
}