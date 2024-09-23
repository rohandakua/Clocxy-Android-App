package com.example.clockappbyrohan.domain.Functions

fun formatString(input: String): String {
    return if (input.length == 1) {
        "0$input"
    } else {
        input
    }
}
