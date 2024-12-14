package com.clocxy.clocxyone.domain.Functions

fun formatString(input: String): String {
    return if (input.length == 1) {
        "0$input"
    } else {
        input
    }
}
