package com.example.clockappbyrohan.domain.usecase.alarmUsecase

import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.domain.repositoryInterface.alarmSchedulerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetAllAlarm (private val alarmSchedulerInterface: alarmSchedulerInterface) {
    fun execute():List<Alarms>{
        return alarmSchedulerInterface.getAllAlarm()
    }
}