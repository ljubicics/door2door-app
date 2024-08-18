package com.example.door2door_app.profile.ui.components.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.time.ZoneId

data class RegisteredDate(
    val day: Int = 1,
    val month: Month = Month.JANUARY,
    val year: Year = Year.now()
)

object TimeRegisteredResolver {
    fun resolveTimeRegistered(timeRegitered: Long): RegisteredDate {
        val instant = Instant.ofEpochMilli(timeRegitered)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val month = localDateTime.month
        val day = localDateTime.dayOfMonth
        val year = localDateTime.year

        return RegisteredDate(day = day, month = month, year = Year.of(year))
    }
}