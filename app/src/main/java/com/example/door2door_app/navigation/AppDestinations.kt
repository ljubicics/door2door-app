package com.example.door2door_app.navigation

import kotlinx.serialization.Serializable

interface IDestination

sealed class AppDestinations : IDestination {
    @Serializable
    data object SplashScreen : AppDestinations()

    @Serializable
    data class LoginScreenPath(val path: String = "") : AppDestinations()

    @Serializable
    data object HomeScreenPath : AppDestinations()

    @Serializable
    data object RegisterScreenPath : AppDestinations()
}