package com.example.clockappbyrohan.domain.Functions

import kotlin.math.min

/**
 * fun getMsFromHoursAndMinutes() is used to get the milliseconds from the hours and minutes.
 * @param hours : hours from 0 to 24
 * @param minutes : minutes from 0 to 59
 * @return a long value that is the total milliseconds
 * this function calculates the time in milliseconds from the hours and minutes.
 */
fun getMsFromHoursAndMinutes(hours:String,minutes:String):Long {
    return (60*hours.toLong() + minutes.toLong())*60000L // this is the formula of getting the millisecond time from hours and minutes.
}