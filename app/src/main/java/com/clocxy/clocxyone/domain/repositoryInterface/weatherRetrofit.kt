package com.clocxy.clocxyone.domain.repositoryInterface

import com.clocxy.clocxyone.domain.dataclass.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query


public interface weatherRetrofit {

    @GET("weather")
    suspend fun getWeatherReport(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("appid") apiKey : String
    ) : WeatherData


}
