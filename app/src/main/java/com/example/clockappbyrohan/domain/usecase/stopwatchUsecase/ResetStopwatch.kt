package com.example.clockappbyrohan.domain.usecase.stopwatchUsecase

import com.example.clockappbyrohan.domain.repositoryInterface.StopwatchRepository

class ResetStopwatch (private val stopwatchRepository: StopwatchRepository) {
    fun execute() {
        stopwatchRepository.reset()
    }
}