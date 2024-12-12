package com.example.clockappbyrohan.domain.usecase.alarmUsecase

import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.domain.dataclass.Event
import com.example.clockappbyrohan.domain.repositoryInterface.alarmSchedulerInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * execute() is used to schedule the alarm.
 * here we are passing the id as 0 in the alarmItem parameter. Room will autogenerate the id.
 * @return Event to notify the user if it is a success or not
 */
class ScheduleAlarm(private  val alarmSchedulerInterface: alarmSchedulerInterface) {
    suspend fun execute(alarm:Alarms , callback : (Event)->Unit ){
        withContext(Dispatchers.IO){
            val event = async { alarmSchedulerInterface.schedule(alarm)}.await()
            withContext(Dispatchers.Main){
                callback(event)
            }

        }
    }

}