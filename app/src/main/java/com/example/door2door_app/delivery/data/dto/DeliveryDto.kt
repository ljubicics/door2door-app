package com.example.door2door_app.delivery.data.dto

import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.user.data.model.AccountDto
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryDto(
    val timeStarted: Long = 0,
    val timeDelivered: Long = 0,
    val trackingCode: String? = null,
    val status: DeliveryStatus? = null,
    val qrConfirmed: Boolean = false,
    val pickupLocation: String = "",
    val deliveryLocation: String = "",
    val sender: AccountDto? = null,
    val receiver: AccountDto? = null,
    val driver: AccountDto? = null
)