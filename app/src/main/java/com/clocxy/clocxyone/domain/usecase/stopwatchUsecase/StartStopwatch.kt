package com.clocxy.clocxyone.domain.usecase.stopwatchUsecase

import com.clocxy.clocxyone.domain.repositoryInterface.StopwatchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartStopwatch (private val stopwatchRepository: StopwatchRepository) {
    fun execute() {
        CoroutineScope(Dispatchers.IO).launch {
            stopwatchRepository.start()
        }
    }
}