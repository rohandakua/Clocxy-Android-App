package com.clocxy.clocxyone.domain.usecase.stopwatchUsecase

import com.clocxy.clocxyone.domain.repositoryInterface.StopwatchRepository

class PauseStopwatch(private val repository: StopwatchRepository) {
    fun execute(){
        repository.pause()
    }
}