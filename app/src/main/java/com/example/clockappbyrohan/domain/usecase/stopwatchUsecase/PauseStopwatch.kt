package com.example.clockappbyrohan.domain.usecase.stopwatchUsecase

import com.example.clockappbyrohan.domain.repositoryInterface.StopwatchRepository

class PauseStopwatch(private val repository: StopwatchRepository) {
    fun execute(){
        repository.pause()
    }
}