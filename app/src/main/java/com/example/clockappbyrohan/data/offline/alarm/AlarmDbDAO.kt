package com.example.clockappbyrohan.data.offline.alarm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDbDAO{
    /**
     * insetAlarm() fun is used to insert the alarm into the database.
     * @param alarmsObj : it is the data of the alarm that is to be inserted.
     * you can get it from the getAlarmById() fun.
     */
    @Insert
    fun insertAlarm(alarmsObj:Alarms)

    /**
     * updateAlarm() fun is used to update the alarm in the database.
     * @param alarmsObj : it is the updated data of the alarm that is to be updated.
     */
    @Update
    fun updateAlarm(alarmsObj:Alarms)

    /**
     * deleteAlarm() fun is used to delete the alarm from the database.
     * @param alarmsObj : it is the data of the alarm that is to be deleted.
     * you can get it from getAlarmById() fun.
     */
    @Delete
    fun deleteAlarm(alarmsObj: Alarms)

    /**
     * getAllAlarms() fun is used to get all the alarms from the database.
     * @return List<Alarms> : it is the list of the alarms that are in the database.
     * this will return all the alarm in the ascending order of their time in ms.
     */
    @Query("SELECT * FROM alarm_db ORDER BY timeInMs ASC")
    fun getAllAlarms(): List<Alarms>

    /**
     * deleteAlarmById() fun is used to delete the alarm from the database by its id.
     * @param id : it is the id of the alarm that is to be deleted.
     */
    @Query("DELETE FROM alarm_db WHERE id = :id")
    fun deleteAlarmById(id: Int)


    /**
     * getAlarmById() fun is used to get the alarm from the database by its id.
     * @param id : it is the id of the alarm that is to be fetched.
     */

    @Query("SELECT * FROM alarm_db WHERE id = :id")
    fun getAlarmById(id: Int):Flow<Alarms>

    /**
     * deleteOldAlarms() fun is used to delete the alarms that are older than the current time.
     * it will run periodically at a interval of 6 hours
     */
    @Query("DELETE FROM alarm_db WHERE timeInMs < :currentTimeInMs")
    fun deleteOldAlarms(currentTimeInMs: Long )
}