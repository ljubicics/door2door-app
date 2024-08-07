package com.example.door2door_app.navigation

import kotlinx.serialization.Serializable

interface IDestination

sealed class AppDestinations : IDestination {
    @Serializable
    data class LoginScreenPath(val path: String = "") : AppDestinations()

    @Serializable
    data object HomeScreenPath : AppDestinations()

    @Serializable
    data object RegisterScreenPath : AppDestinations()
}

sealed class MainDestinations : IDestination {

    @Serializable
    data object MainScreenPath : MainDestinations()

    @Serializable
    data object DeliveryScreenPath : MainDestinations()
}