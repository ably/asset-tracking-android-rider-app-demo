package com.ably.tracking.demo.publisher.common

fun String.canParseToDouble() =
    toDoubleOrNull() != null
