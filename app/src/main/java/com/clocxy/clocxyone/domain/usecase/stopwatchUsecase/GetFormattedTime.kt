package com.clocxy.clocxyone.domain.usecase.stopwatchUsecase

import com.clocxy.clocxyone.domain.Functions.milliSecondsToTime
import com.clocxy.clocxyone.domain.repositoryInterface.StopwatchRepository

class GetFormattedTime(private val stopwatchRepository: StopwatchRepository) {
    fun execute():String{
        return milliSecondsToTime(stopwatchRepository.getTimeInMillis())
    }
}