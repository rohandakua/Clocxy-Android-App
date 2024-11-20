package com.example.clockappbyrohan.hilt

import android.app.AlarmManager
import android.content.Context
import com.example.clockappbyrohan.data.offline.StopwatchRepositoryImplementation
import com.example.clockappbyrohan.data.online.API_KEY
import com.example.clockappbyrohan.data.online.BaseUrlWeatherApi
import com.example.clockappbyrohan.domain.repositoryInterface.StopwatchRepository
import com.example.clockappbyrohan.domain.repositoryInterface.weatherRetrofit
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.GetFormattedTime
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.PauseStopwatch
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.ResetStopwatch
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.StartStopwatch
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

    @Provides
    @Singleton
    fun providesStopwatchRepository ():StopwatchRepository{
        //TODO provide a stop watch repository
        return StopwatchRepositoryImplementation()

    }
    @Provides
    fun providesGetFormattedTimeUsecase(
        stopwatchRepository: StopwatchRepository
    ):GetFormattedTime{
        return GetFormattedTime(stopwatchRepository)
    }
    @Provides
    fun providesPauseStopwatch(
        stopwatchRepository: StopwatchRepository
    ):PauseStopwatch{
        return PauseStopwatch(stopwatchRepository)
    }
    @Provides
    fun providesResetStopwatch(stopwatchRepository: StopwatchRepository):ResetStopwatch{
        return ResetStopwatch(stopwatchRepository)
    }
    @Provides
    fun providesStartStopwatch(stopwatchRepository: StopwatchRepository):StartStopwatch{
        return StartStopwatch(stopwatchRepository)
    }

    @Provides
    @Singleton
    fun provideAlarmManager(context : Context):AlarmManager{
        return context.getSystemService(AlarmManager::class.java)

    }



}