package com.ably.tracking.demo.publisher.ably.log

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DateFormatterFactory(
    private val locale: Locale = Locale.getDefault(),
    private val timeZone: TimeZone = TimeZone.getDefault()
) {

    fun buildFormatterFor(pattern: String): DateFormat =
        SimpleDateFormat(pattern, locale).also {
            it.timeZone = timeZone
        }
}
