package com.example.clockappbyrohan.presentation.ViewModels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clockappbyrohan.data.online.API_KEY
import com.example.clockappbyrohan.domain.Functions.KelvinToCelsius
import com.example.clockappbyrohan.domain.repositoryInterface.weatherRetrofit
import com.example.clockappbyrohan.presentation.Activity.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

// weather api
// https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val weatherRetrofit: weatherRetrofit,
    @ApplicationContext private val context: Context
) : ViewModel() {
    public fun dosth() {
        //Log.d("kyya", "ho rha ha")
    }

    private val _hour: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    var hour: StateFlow<Int?> = _hour
        private set

    fun setHour(hourToSet: Int?) {
        _hour.value = hourToSet
    }

    private val _minute: MutableStateFlow<Int?> = MutableStateFlow<Int?>(null)
    var minute: StateFlow<Int?> = _minute
        private set

    fun setMinute(minuteToSet: Int?) {
        _minute.value = minuteToSet
    }


    private val _day: MutableStateFlow<String?> = MutableStateFlow<String?>(null)
    var day: StateFlow<String?> = _day
        private set

    fun setDay(dayToSet: String?) {
        _day.value = dayToSet
    }

    private val _date: MutableStateFlow<String?> = MutableStateFlow<String?>(null)
    var date: StateFlow<String?> = _date
        private set

    fun setDate(dateToSet: String?) {
        _date.value = dateToSet
    }

    private val _second: MutableStateFlow<Int> = MutableStateFlow<Int>(0)
    var second: StateFlow<Int> = _second
        private set

    fun setSecond(secondToSet: Int) {
        _second.value = secondToSet
    }

    private val _weatherType: MutableStateFlow<String?> = MutableStateFlow("loading...")
    var weatherType: StateFlow<String?> = _weatherType
        private set

    fun setWeatherType(weatherTypeToSet: String?) {
        _weatherType.value = weatherTypeToSet
    }

    private val _temperature: MutableStateFlow<String?> = MutableStateFlow("loading...")
    var temperature: StateFlow<String?> = _temperature
        private set

    fun setTemperature(temperatureToSet: String?) {
        _temperature.value = temperatureToSet
    }

    private val _temperatureFeelsLike: MutableStateFlow<String?> = MutableStateFlow("loading...")
    var temperatureFeelsLike: StateFlow<String?> = _temperatureFeelsLike
        private set

    fun setTemperatureFeelsLike(temperatureToSet: String?) {
        _temperatureFeelsLike.value = temperatureToSet
    }


    private val _rainChance: MutableStateFlow<String?> = MutableStateFlow("loading...")
    var rainChance: StateFlow<String?> = _rainChance
        private set

    fun setRainChance(rainChanceToSet: String?) {
        _rainChance.value = rainChanceToSet
    }


    private val _locationData = MutableLiveData<Location?>()
    var locationData: MutableLiveData<Location?> = _locationData
        private set

    fun setLocationData(locationToSet: Location?) {
        _locationData.value = locationToSet
    }
    var triedTimes=0;

    //    @SuppressLint("SupportAnnotationUsage")
//    @RequiresPermission(
//        anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION]
//    )
    @SuppressLint("MissingPermission")
    private fun fetchLocation(context: Context) {

            try {
                val locationTask =LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { location: Location? ->
                    setLocationData(location)
                    Log.d("location", "${location?.latitude} ${location?.longitude}")
                }.addOnFailureListener { exception ->
                    val location = Location("gps")
                    location.latitude = 28.6139
                    location.longitude = 77.2090 // defaulting to location of delhi
                    setLocationData(location)
                }
            } catch (e: Exception) {
                val location = Location("gps")
                location.latitude = 28.6139
                location.longitude = 77.2090 // defaulting to location of delhi
                setLocationData(location)
                Log.d("fetchLoction", "${e.message}, \n failed")
            }


    }
//  https://api.openweathermap.org/data/2.5/28.6139/77.2090/?appid=30f3a06873d4abe929a4ed17c017bf3e
    private suspend fun fetchWeatherData() {
        try {
            withContext(Dispatchers.IO) {


                fetchLocation(context)
                delay(1000)
                val weatherReport = async {

                    if(locationData.value==null && triedTimes>4){
                        weatherRetrofit.getWeatherReport(
                            28.6139,
                            77.2090
                            ,
                            API_KEY
                        )


                    }else{
                        weatherRetrofit.getWeatherReport(
                            locationData.value!!.latitude,
                            locationData.value!!.longitude,
                            API_KEY
                        )

                    }

                }
                triedTimes++;
                Log.d("triedTimes",triedTimes.toString())
                val report = weatherReport.await()
                setTemperature(KelvinToCelsius(report.main.temp).toString().substring(0,4)+" C")
                setWeatherType(report.weather[0].main)
                setRainChance(report.main.humidity.toString())
                setTemperatureFeelsLike("feels like "+(KelvinToCelsius(report.main.feels_like).toString().substring(0,4))+" C")

                Log.d("fetchWeatherData", "${weatherReport.await().weather[0].main}")
            }


        } catch (e: Exception) {
            Log.d("getWeatherData", "${e.message}")
        }
    }

    public suspend fun updateWeather() {
        while (true) {
            fetchWeatherData()
            delay(if(locationData.value!=null) 1000*60*2 else 3000)    //every 15 minutes it checks for the last location of the user and deals with the same
            //if(locationData.value==null) 1000*60*2 else 3000
        }
    }


    private suspend fun getDateAndTime() {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss,EEEE", Locale.getDefault())
        dateFormat.timeZone =
            TimeZone.getTimeZone("Asia/Kolkata")   // for getting the time of India
        val currentDateAndTime = Calendar.getInstance().time
        val formattedDateAndTime = dateFormat.format(currentDateAndTime)
        // setting the date and time
        setDate(formattedDateAndTime.substring(0, 11))
        setDay(formattedDateAndTime.substring(20))
        setHour(formattedDateAndTime.substring(11, 13).toInt())
        setMinute(formattedDateAndTime.substring(14, 16).toInt())
        setSecond(formattedDateAndTime.substring(17, 19).toInt())


    }

    private suspend fun updateDate() {
        while (true) {
            getDateAndTime()
            //Log.d("time", "${hour.value}  ${minute.value}")

            delay(Math.max(0, 1000 * (60 - second.value)).toLong())
            // adding this delay for getting the data every minute
        }
    }


    init {

        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                updateDate()
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateWeather()

            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss,EEEE", Locale.getDefault())
                dateFormat.timeZone =
                    TimeZone.getTimeZone("Asia/Kolkata")   // for getting the time of India
                while (true){
                    val currentDateAndTime = Calendar.getInstance().time
                    val formattedDateAndTime = dateFormat.format(currentDateAndTime)
                    setSecond(formattedDateAndTime.substring(17, 19).toInt())
                    //Log.d("the current time","${formattedDateAndTime}")
                    delay(1000)
                }

            }
        }

    }


}