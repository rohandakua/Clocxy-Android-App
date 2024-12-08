package com.example.clockappbyrohan.domain.usecase.alarmUsecase

import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.domain.dataclass.Event
import com.example.clockappbyrohan.domain.repositoryInterface.alarmSchedulerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * execute() is used to update the alarm.
 * it is a callback function that triggers when the result has come.
 * usage:
 * UpdateAlarm.execute(alarm) { event ->
 *     when (event) {
 *         Event.SUCCESS -> {
 *             // Handle success
 *         }
 *         Event.FAILURE -> {
 *             // Handle failure
 *         }
 *     }
 * }
 * Here alarmId is the id of the alarm that is to be updated. Now check if it works properly or not. ??TODO
 */
class UpdateAlarm (private val alarmSchedulerInterface: alarmSchedulerInterface){
    fun execute(alarm:Alarms , callback: (Event)->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val event = async { alarmSchedulerInterface.updateAlarm(alarm) }.await()
            withContext(Dispatchers.Main){
                callback(event)
            }
        }

    }
}