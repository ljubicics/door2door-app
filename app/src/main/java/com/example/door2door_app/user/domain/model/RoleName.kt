package com.example.door2door_app.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class RoleName {
    ROLE_CUSTOMER,
    ROLE_DELIVERY,
    UNKNOWN
}