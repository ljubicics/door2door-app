package com.example.door2door_app.login.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token : String = ""
)
