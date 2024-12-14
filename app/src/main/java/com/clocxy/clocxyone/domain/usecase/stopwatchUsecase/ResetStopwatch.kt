package com.clocxy.clocxyone.domain.usecase.stopwatchUsecase

import com.clocxy.clocxyone.domain.repositoryInterface.StopwatchRepository

class ResetStopwatch (private val stopwatchRepository: StopwatchRepository) {
    fun execute() {
        stopwatchRepository.reset()
    }
}