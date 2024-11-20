package com.example.clockappbyrohan.domain.Functions

fun milliSecondsToTime(ms:Long):String {
    // returns a string that has hours : minutes : seconds : milliseconds
    val Ms = ms % 1000
    val seconds = (ms / 1000) % 60
    val minutes = ((ms/1000)/60) % 60
    val hours = (((ms/1000)/60)/60) % 24
    return "$hours : $minutes : $seconds : $Ms"
}
