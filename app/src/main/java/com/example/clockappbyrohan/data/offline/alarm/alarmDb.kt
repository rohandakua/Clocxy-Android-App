package com.example.clockappbyrohan.data.offline.alarm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alarms::class] , version = 1, exportSchema = false)
abstract class alarmDb : RoomDatabase(){
    abstract fun alarmDbDAO(): AlarmDbDAO

    companion object{
        private var INSTANCE : alarmDb? = null
        fun getAlarmDb(context: Context): alarmDb{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    alarmDb::class.java,
                    "alarm_db"
                ).build()
                INSTANCE=instance
                instance
            }
        }

    }

}