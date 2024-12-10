package com.example.clockappbyrohan.domain.Functions

/**
 * getMinutes() is used to extract the minutes from a timeInMs
 * @param timeInMs: is the time in milliseconds and it only ranges from 0hrs to 24 hrs.
 * @return minutes value in string ranging from 0 to 59.
 */
fun getMinutes(timeInMs:Long):String {
    val minutes = (((timeInMs/1000)/60)%60)
    return minutes.toString().padStart(2,'0')
}