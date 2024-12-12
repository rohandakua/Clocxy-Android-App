package com.example.clockappbyrohan.domain.repositoryInterface

import com.example.clockappbyrohan.data.offline.alarm.Alarms
import com.example.clockappbyrohan.domain.dataclass.Event
import kotlinx.coroutines.flow.Flow

interface alarmSchedulerInterface {
    /**
     * schedule() fun is used to schedule the alarm.
     *@param alarmItem : it is the data of the alarm that is to be scheduled.
     * @return Event to notify the user if it is a success or not
     */

    suspend fun schedule(alarmItem: Alarms) : Event

    /**
     * cancel() fun is used to cancel the alarm.
     * @param alarmItem : it is the data of the alarm that is to be cancelled.
     * @return Event to notify the user if it is a success or not
     */
    fun cancel(alarmItem: Alarms):Event
    /**
     * cancel() fun is used to cancel the alarm.
     * @param alarmId : it is the id of the alarm that is to be cancelled.
     * @return Event to notify the user if it is a success or not
     */
    suspend fun cancel(alarmId: Int):Event

    /**
     * rescheduleAllAlarm() fun is used to reschedule all the alarms.
     * fetch all the alarms that are active and schedule them
     * @return Event to notify the user if it is a success or not
     */
    suspend fun rescheduleAllAlarm():Event

    /**
     * updateAlarm() fun is used to update the alarm.
     * @param alarmItem: it is the updated data of the alarm that is to be updated.
     * @return Event to notify the user.
     */
    suspend fun updateAlarm(alarmItem: Alarms): Event

    /**
     * getAllAlarm() fun is used to get all the alarms.
     */
    suspend fun getAllAlarm(): List<Alarms>


}