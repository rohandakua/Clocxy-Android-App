package com.example.clockappbyrohan.domain.Functions

/**
 * getId() function is used to get the id of the alarm.
 * @return a int having last digit as indicator of day of the week.
 */
fun getId(
    oldId:Int,
    weekDay:Int
) : Int {
    return oldId*10 + weekDay

}