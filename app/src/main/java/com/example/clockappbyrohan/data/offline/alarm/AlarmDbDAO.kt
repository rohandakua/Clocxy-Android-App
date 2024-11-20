package com.example.clockappbyrohan.data.offline.alarm

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface AlarmDbDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmsObj:Alarms)

    @Update
    suspend fun updateAlarm(alarmsObj:Alarms)

    @Delete
    suspend fun deleteAlarm(alarmsObj: Alarms)

    @Query("SELECT * FROM alarm_db ORDER BY timeInMs ASC")
    fun getAllAlarms(): Flow<List<Alarms>>

    @Query("DELETE FROM alarm_db WHERE id = :id")
    suspend fun deleteAlarmById(id: Int)

}