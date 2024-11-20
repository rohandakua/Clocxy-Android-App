package com.example.clockappbyrohan.domain.repositoryInterface

import com.example.clockappbyrohan.data.offline.alarm.Alarms

interface alarmSchedulerInterface {
    fun schedule(alarmItem: Alarms)
    fun cancel(alarmItem: Alarms)
}