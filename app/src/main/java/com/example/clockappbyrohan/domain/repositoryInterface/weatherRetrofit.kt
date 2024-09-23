package com.example.clockappbyrohan.domain.repositoryInterface

import android.location.Location
import com.example.clockappbyrohan.domain.dataclass.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

//https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=30f3a06873d4abe929a4ed17c017bf3e

public interface weatherRetrofit {

    @GET("weather")
    suspend fun getWeatherReport(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("appid") apiKey : String
    ) : WeatherData


}