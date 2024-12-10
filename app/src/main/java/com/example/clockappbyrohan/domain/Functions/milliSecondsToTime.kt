package com.example.clockappbyrohan.domain.Functions

fun milliSecondsToTime(ms: Long): String {
    // Extract time components
    val Ms = (ms % 1000)/10
    val seconds = (ms / 1000) % 60
    val minutes = ((ms / 1000) / 60) % 60
    val hours = (((ms / 1000) / 60) / 60) % 24

    // Format each component to be exactly two characters long
    return "%02d : %02d : %02d : %02d".format(hours, minutes, seconds, Ms)
}

