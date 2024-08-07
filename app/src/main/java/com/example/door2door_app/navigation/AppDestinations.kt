package com.example.door2door_app.navigation

import kotlinx.serialization.Serializable

interface IDestination

sealed class AppDestinations : IDestination {
    @Serializable
    data object SplashScreenPath : AppDestinations()

    @Serializable
    data class LoginScreenPath(val path: String = "") : AppDestinations()

    @Serializable
    data object MainScreenPath : AppDestinations()

    @Serializable
    data object RegisterScreenPath : AppDestinations()
}

sealed class MainDestinations : IDestination {

    @Serializable
    data object HomeScreenPath : MainDestinations()

    @Serializable
    data object DeliveryScreenPath : MainDestinations()
}