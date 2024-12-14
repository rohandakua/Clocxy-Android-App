package com.clocxy.clocxyone.domain.usecase.alarmUsecase

import com.clocxy.clocxyone.data.offline.alarm.Alarms
import com.clocxy.clocxyone.domain.repositoryInterface.alarmSchedulerInterface

class GetAllAlarm(private val alarmSchedulerInterface: alarmSchedulerInterface) {
    suspend fun execute(): List<Alarms> {
        return alarmSchedulerInterface.getAllAlarm()
    }
}