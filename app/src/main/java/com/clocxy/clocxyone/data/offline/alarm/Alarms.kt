package com.clocxy.clocxyone.data.offline.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * the table name is alarm_db
 */
@Entity(tableName = "alarm_db")
data class Alarms (
    /**
     * id of the alarm
     */
    @PrimaryKey(autoGenerate = true) val id: Int = 0 ,
    /**
     * name of the alarm, it can be null
     */
    @ColumnInfo val name: String?="alarm name",
    /**
     * timeInMs stores the exact time in milli seconds in db
     * this should be a time between 0 hrs to 24 hrs , as this is the time of the day when the alarm will go on
     */
    @ColumnInfo  val timeInMs: Long=28800000,

    /**
     * this are for the purpose of repeating the alarm on specific days
     */
    @ColumnInfo val isMonday: Boolean = false,
    @ColumnInfo val isTuesday: Boolean = false,
    @ColumnInfo val isWednesday: Boolean = false,
    @ColumnInfo val isThursday: Boolean = false,
    @ColumnInfo val isFriday: Boolean = false,
    @ColumnInfo val isSaturday: Boolean = false,
    @ColumnInfo val isSunday: Boolean = false
)

