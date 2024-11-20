package com.example.clockappbyrohan.data.offline.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_db")
data class Alarms (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val timeInMs: Long
)

