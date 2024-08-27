package com.example.door2door_app.user.data.model

import com.example.door2door_app.user.domain.model.Role
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: Long,
    val username: String,
    val role: Role,
    val numberOfDeliveries: Int,
)
