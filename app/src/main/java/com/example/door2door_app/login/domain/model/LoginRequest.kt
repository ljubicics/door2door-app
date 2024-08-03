package com.example.door2door_app.login.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String = "",
    val password: String = ""
)
