package com.example.clockappbyrohan.hilt

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.room.Room
import com.example.clockappbyrohan.data.offline.StopwatchRepositoryImplementation
import com.example.clockappbyrohan.data.offline.alarm.AlarmDbDAO
import com.example.clockappbyrohan.data.offline.alarm.AlarmSchedulerInterfaceImplementation
import com.example.clockappbyrohan.data.offline.alarm.Week
import com.example.clockappbyrohan.data.offline.alarm.AlarmDb
import com.example.clockappbyrohan.data.online.API_KEY
import com.example.clockappbyrohan.data.online.BaseUrlWeatherApi
import com.example.clockappbyrohan.domain.repositoryInterface.StopwatchRepository
import com.example.clockappbyrohan.domain.repositoryInterface.alarmSchedulerInterface
import com.example.clockappbyrohan.domain.repositoryInterface.weatherRetrofit
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.CancelAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.DeleteAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.GetAllAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.ScheduleAlarm
import com.example.clockappbyrohan.domain.usecase.alarmUsecase.UpdateAlarm
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.GetFormattedTime
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.PauseStopwatch
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.ResetStopwatch
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.StartStopwatch
import com.example.clockappbyrohan.framework.MediaPlayerManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.Reusable
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
    fun provideAlarmManager(@ApplicationContext context : Context):AlarmManager{
        return context.getSystemService(AlarmManager::class.java)

    }

    @Provides
    fun providesUserDao(@ApplicationContext context: Context,
                                  alarmDbObj: AlarmDb): AlarmDbDAO{
        return alarmDbObj.AlarmDbDAO()
    }
    @Provides
    @Singleton
    fun provideAlarmDB(@ApplicationContext context: Context): AlarmDb {
        return Room.databaseBuilder(
            context,
            AlarmDb::class.java,
            "alarm_db"
        ).build()
    }

    @Provides
    fun provideMediaPlayer(
        @ApplicationContext context: Context
    ):MediaPlayer{
        return try {MediaPlayer.create(context,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))}
        catch (e:Exception){
            throw RuntimeException("Error in creating media player")
        }

    }
    @Provides
    @Singleton
    fun provideMediaPlayerManager(
        @ApplicationContext context: Context
    ):MediaPlayerManager{
        return try {
            MediaPlayerManager(context)
        }catch (e:Exception){
            throw RuntimeException("Error in creating media player manager")
        }
    }

    @Provides
    fun providesNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager{
        return try {
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager
        }catch (e : Exception){
            throw RuntimeException("Error in creating notification manager")
        }
    }

    @Provides
    fun providesAlarmSchedulerInterface(
        alarmDbDAO: AlarmDbDAO,
        @ApplicationContext context: Context,
        alarmManager: AlarmManager,
        week: Week
    ): alarmSchedulerInterface {
        return AlarmSchedulerInterfaceImplementation(alarmDbDAO, context, alarmManager,week)
    }

    @Provides
    fun providesCancelAlarmUseCase(alarmSchedulerInterface: alarmSchedulerInterface): CancelAlarm {
        return CancelAlarm(alarmSchedulerInterface)
    }

    @Provides
    fun providesDeleteAlarmUseCase(alarmSchedulerInterface: alarmSchedulerInterface): DeleteAlarm {
        return DeleteAlarm(alarmSchedulerInterface)
    }
    @Provides
    fun providesScheduleAlarmUseCase(alarmSchedulerInterface: alarmSchedulerInterface): ScheduleAlarm{
        return ScheduleAlarm(alarmSchedulerInterface)
    }
    @Provides
    fun providesUpdateAlarmUseCase(alarmSchedulerInterface: alarmSchedulerInterface): UpdateAlarm{
        return UpdateAlarm(alarmSchedulerInterface)
    }
    @Provides
    fun providesWeek():Week{
        return Week()
    }
    @Provides
    fun providesGetAllAlarm(alarmSchedulerInterface: alarmSchedulerInterface):GetAllAlarm{
        return GetAllAlarm(alarmSchedulerInterface)
    }



}