package com.example.clockappbyrohan.domain.Functions

import java.time.LocalTime

/**
 * getCurrentHoursAndMinutes() is used to get the current hours and minutes.
 * it return a pair of hours and minutes.
 * the hours and minutes are current hours and minutes.
 */
fun getCurrentHoursAndMinutes(): Pair<String, String> {
    val currentTime = LocalTime.now()
    val hours = currentTime.hour.toString().padStart(2, '0') // Format as "HH"
    val minutes = currentTime.minute.toString().padStart(2, '0') // Format as "MM"
    return Pair(hours, minutes)
}