package com.example.door2door_app.register.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val email: String,
    val mobileNumber: String,
    val address: String,
)
