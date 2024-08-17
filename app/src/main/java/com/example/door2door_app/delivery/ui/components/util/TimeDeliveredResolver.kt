package com.example.door2door_app.delivery.ui.components.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId

data class DeliveryDayAndMonth(
    val day: Int = 1,
    val month: Month = Month.JANUARY
)

object TimeDeliveredResolver {
    fun resolveTimeDelivered(timeDelivered: Long): DeliveryDayAndMonth {
        val instant = Instant.ofEpochMilli(timeDelivered)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val month = localDateTime.month
        val day = localDateTime.dayOfMonth

        return DeliveryDayAndMonth(day = day, month = month)
    }
}