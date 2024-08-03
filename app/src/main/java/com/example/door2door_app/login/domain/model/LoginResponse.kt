package com.example.door2door_app.login.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token : String = ""
)
