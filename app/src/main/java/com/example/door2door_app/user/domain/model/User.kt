package com.example.door2door_app.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String = "",
    val surname: String = "",
    val email: String? = null,
    val mobileNumber: String? = null,
    val address: String? = null,
    val timeCreated: Long? = null,
)
