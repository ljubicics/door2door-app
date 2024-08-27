package com.example.door2door_app.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: Long = 0,
    val username: String = "",
    val password: String = "",
    val role: RoleName = RoleName.UNKNOWN,
    val numberOfDeliveries: Int = 0,
)
