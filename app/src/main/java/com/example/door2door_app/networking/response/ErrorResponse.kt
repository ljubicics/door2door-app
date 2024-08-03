package com.example.door2door_app.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String? = null
)
