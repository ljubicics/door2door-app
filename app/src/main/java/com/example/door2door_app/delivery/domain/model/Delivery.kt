package com.example.door2door_app.delivery.domain.model

import com.example.door2door_app.user.domain.model.Account
import kotlinx.serialization.Serializable

@Serializable
data class Delivery(
    val id: Long = 0,
    val timeStarted: Long = 0,
    val timeDelivered: Long = 0,
    val trackingCode: String? = null,
    val status: DeliveryStatus? = null,
    val qrConfirmed: Boolean = false,
    val pickupLocation: String = "",
    val deliveryLocation: String = "",
    val sender: Account? = null,
    val receiver: Account? = null,
    val driver: Account? = null
)