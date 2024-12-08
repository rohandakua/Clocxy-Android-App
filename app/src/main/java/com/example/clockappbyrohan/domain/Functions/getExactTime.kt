package com.example.clockappbyrohan.domain.Functions

import java.time.LocalDate
import java.time.ZoneId

/**
 * getExactTime() gives each alarm exact time.
 * to calculate this we take timeInMs and the day(1,2,3,4,5,6,7) i.e. the count from 0 to 6
 * @param timeInMs : time in milliseconds
 * @param day : Count value from 0 to 6
 * @return : Exact time in milliseconds
 * we have get for eg. 10:00 hrs for alarm , and day = 2. This means that we have to set for 10:00 hrs on the 2nd day from today.
 */
fun getExactTime(
    timeInMs:Long,
    day:Int,
) :Long{
    // first get the 0 hrs time of current day
    // then add 24 * day time in it
    // then return the timeInMs + the above value
    val zoneId = ZoneId.systemDefault()
    val todayStartEpoch = LocalDate.now(zoneId).atStartOfDay(zoneId).toEpochSecond()*1000 // this is the 00hrs epoch time of current day
    return todayStartEpoch+timeInMs+(24*60*60*1000*day)
}