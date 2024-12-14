package com.clocxy.clocxyone.domain.usecase.alarmUsecase

import com.clocxy.clocxyone.domain.dataclass.Event
import com.clocxy.clocxyone.domain.repositoryInterface.alarmSchedulerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * execute() is used to cancel the alarm.
 * it is a callback function that triggers when the result has come.
 * usage:
 * cancelAlarm.execute(alarmId) { event ->
 *     when (event) {
 *         Event.SUCCESS -> {
 *             // Handle success
 *         }
 *         Event.FAILURE -> {
 *             // Handle failure
 *         }
 *     }
 * }
 *
 */
class CancelAlarm(private val alarmSchedulerInterface: alarmSchedulerInterface) {
    fun execute(alarmId: Int, callback:(Event) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val event = async { alarmSchedulerInterface.cancel(alarmId) }.await()
            withContext(Dispatchers.Main){
                callback(event)
            }
        }
    }
}