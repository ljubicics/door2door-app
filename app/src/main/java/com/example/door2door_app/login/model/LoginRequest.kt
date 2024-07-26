package com.example.door2door_app.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String = "",
    val password: String = ""
)
