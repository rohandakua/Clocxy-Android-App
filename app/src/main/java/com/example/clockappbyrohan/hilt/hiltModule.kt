package com.example.clockappbyrohan.hilt

import android.content.Context
import com.example.clockappbyrohan.data.online.API_KEY
import com.example.clockappbyrohan.data.online.BaseUrlWeatherApi
import com.example.clockappbyrohan.domain.repositoryInterface.weatherRetrofit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object hiltModule {
    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideBaseUrl(): String = BaseUrlWeatherApi

    @Provides
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().
        baseUrl(provideBaseUrl()).
        addConverterFactory(GsonConverterFactory.create()).
        build()
    }
    @Provides
    @Singleton
    fun providesWeatherApi ( retrofit: Retrofit) : weatherRetrofit{
        return retrofit.create(weatherRetrofit::class.java)
    }


}