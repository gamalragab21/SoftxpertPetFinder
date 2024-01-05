package net.xpert.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.xpert.android.helpers.properties.data.ConfigurationUtil
import net.xpert.android.helpers.properties.domain.IConfigurationUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConfigUtils(@ApplicationContext context: Context): IConfigurationUtil =
        ConfigurationUtil(context, "softxpert-configuration.properties")
}