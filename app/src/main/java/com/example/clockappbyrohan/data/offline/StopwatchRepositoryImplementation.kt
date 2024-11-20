package com.example.clockappbyrohan.data.offline

import com.example.clockappbyrohan.domain.repositoryInterface.StopwatchRepository
import kotlinx.coroutines.delay

class StopwatchRepositoryImplementation : StopwatchRepository {
    private var timeInMillis : Long = 0L
    private var isRunning : Boolean = false

    init {
        timeInMillis=0L
        isRunning=false
    }
    override suspend fun start() {
        isRunning= true
        while(isRunning){
            delay(10L)// make changes in every 10 milli seconds
            timeInMillis=+10
        }
    }

    override fun reset() {
        isRunning=false
        timeInMillis=0L
    }

    override fun pause() {
        isRunning=false
    }

    override fun getTimeInMillis(): Long {
        return timeInMillis
    }

    override fun isRunning(): Boolean {
        return isRunning
    }


}