package com.example.door2door_app.user.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val mobileNumber: String? = null,
    val address: String? = null,
    val timeCreated: Long? = null,
    val totalDeliveries: Int? = null,
)