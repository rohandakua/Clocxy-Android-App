package com.example.clockappbyrohan.domain.usecase.stopwatchUsecase

import com.example.clockappbyrohan.domain.Functions.milliSecondsToTime
import com.example.clockappbyrohan.domain.repositoryInterface.StopwatchRepository

class GetFormattedTime(private val stopwatchRepository: StopwatchRepository) {
    fun execute():String{
        return milliSecondsToTime(stopwatchRepository.getTimeInMillis())
    }
}