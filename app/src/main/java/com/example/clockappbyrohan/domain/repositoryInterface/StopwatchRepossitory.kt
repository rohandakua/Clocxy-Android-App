package com.example.clockappbyrohan.domain.repositoryInterface

interface StopwatchRepository {
    suspend fun start()
    fun reset()
    fun pause()
    fun getTimeInMillis():Long
    fun isRunning():Boolean

}