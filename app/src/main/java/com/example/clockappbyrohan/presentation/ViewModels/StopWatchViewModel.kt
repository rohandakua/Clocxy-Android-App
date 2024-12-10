package com.example.clockappbyrohan.presentation.ViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.GetFormattedTime
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.PauseStopwatch
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.ResetStopwatch
import com.example.clockappbyrohan.domain.usecase.stopwatchUsecase.StartStopwatch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StopWatchViewModel @Inject constructor(
    private val startStopwatch: StartStopwatch,
    private val pauseStopwatch: PauseStopwatch,
    private val resetStopwatch: ResetStopwatch,
    private val getFormattedTime: GetFormattedTime,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private var _time = MutableStateFlow<String>("00 : 00 : 00 : 00")  // HH : MM : SS : msms
    val time: StateFlow<String> get() = _time

    private val _isRunning = MutableStateFlow<Boolean>(false)
    val isRunning: StateFlow<Boolean> get() = _isRunning

    fun startStopwatch() {
        _isRunning.value = true
        startStopwatch.execute()
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                while (_isRunning.value) {
                    delay(5L)  // every 5 milliseconds it will update the time in the stopWatch
                    _time.value = getFormattedTime.execute()
                }
            }
        }
    }

    fun pauseStopwatch() {
        _isRunning.value = false
        pauseStopwatch.execute()
    }

    fun resetStopwatch() {
        _isRunning.value = false
        resetStopwatch.execute()
        _time.value= getFormattedTime.execute()

    }

    fun startStop() {
        println("startStop clicked")
        if (_isRunning.value == false) {
            startStopwatch()
        } else {
            pauseStopwatch()
        }
    }


}

