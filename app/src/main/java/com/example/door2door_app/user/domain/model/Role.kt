package com.example.door2door_app.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Role(
    val id: Long,
    val name: RoleName
)