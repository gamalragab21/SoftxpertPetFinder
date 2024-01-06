package net.soft.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.soft.android.helpers.properties.domain.IConfigurationUtil
import net.soft.core.BuildConfig
import net.soft.core.common.data.repository.remote.PetFinderApiService
import net.soft.core.common.data.repository.remote.PetFinderHeaderInterceptor
import net.soft.core.common.data.repository.remote.RetrofitNetworkProvider
import net.soft.core.common.data.repository.remote.converter.ResponseBodyConverter
import net.soft.core.common.data.repository.remote.factory.LeonCallAdapterFactory
import net.soft.core.common.domain.repository.remote.INetworkProvider
import net.soft.features.getUserTokenUC.domain.interactor.GetLocalUserTokenUC
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providePetFinderApiService(retrofit: Retrofit): PetFinderApiService =
        retrofit.create(PetFinderApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitNetwork(petFinderApiService: PetFinderApiService): INetworkProvider =
        RetrofitNetworkProvider(petFinderApiService)

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient.Builder,
        configurationUtil: IConfigurationUtil
    ): Retrofit =
        Retrofit.Builder().client(okHttpClient.build())
            .baseUrl(configurationUtil.getServerBaseUrl())
            .addConverterFactory(gsonFactory)
            .addCallAdapterFactory(LeonCallAdapterFactory.create(ResponseBodyConverter())).build()

    @Provides
    @Singleton
    fun provideGsonFactory(): GsonConverterFactory =
        GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor, headerInterceptor: PetFinderHeaderInterceptor
    ) = OkHttpClient().newBuilder().apply {
        connectTimeout(30L, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        connectionPool(
            ConnectionPool(30L.toInt(), 500000, TimeUnit.MILLISECONDS)
        )
        readTimeout(30L, TimeUnit.SECONDS)
        writeTimeout(30L, TimeUnit.SECONDS)
        addInterceptor(interceptor)
        addInterceptor(headerInterceptor)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun providePetFinderHeaderInterceptor(getLocalUserTokenUC: GetLocalUserTokenUC): PetFinderHeaderInterceptor =
        PetFinderHeaderInterceptor(getLocalUserTokenUC)
}