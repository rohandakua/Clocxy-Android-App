package com.clocxy.clocxyone.domain.Functions

/**
 * getHours() is used to get the hours from the timeInMs.
 * @return hours from 0 to 24
 * @param : timeInMs this is total time in milliseconds
 */
fun getHours(timeInMs:Long):String {
    val hours = (((timeInMs/1000)/60)/60)
    return hours.toString().padStart(2,'0')
}
