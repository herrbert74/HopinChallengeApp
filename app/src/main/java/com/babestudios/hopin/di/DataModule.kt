package com.babestudios.hopin.di

import com.babestudios.hopin.BuildConfig
import com.babestudios.hopin.data.network.HopinRepository
import com.babestudios.hopin.data.network.HopinRepositoryContract
import com.babestudios.hopin.data.network.HopinService
import com.babestudios.hopin.navigation.HopinNavigation
import com.babestudios.hopin.navigation.HopinNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class DataModule {
    @Suppress("DEPRECATION")
    @Provides
    @Singleton
    internal fun provideHopinRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(logging)
        return Retrofit.Builder()//
            .baseUrl(BuildConfig.HOPIN_BASE_URL)//
            .addConverterFactory(GsonConverterFactory.create())//
            .client(httpClient.build())//
            .build()
    }

    @Provides
    @Singleton
    internal fun provideHopinService(retroFit: Retrofit): HopinService {
        return retroFit.create(HopinService::class.java)
    }

    @Provides
    @Singleton
    fun provideHopinRepository(hopinService: HopinService): HopinRepositoryContract {
        return HopinRepository(hopinService)
    }

    @Provides
    @Singleton
    fun provideHopinNavigator(): HopinNavigator {
        return HopinNavigation()
    }
}