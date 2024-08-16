package com.example.door2door_app.navigation

import kotlinx.serialization.Serializable

interface IDestination

sealed class AppDestinations : IDestination {
    @Serializable
    data object SplashScreenPath : AppDestinations()

    @Serializable
    data class LoginScreenPath(val path: String = "") : AppDestinations()

    @Serializable
    data object Customer : AppDestinations()

    @Serializable
    data object DeliveryDriver : AppDestinations()

    @Serializable
    data object RegisterScreenPath : AppDestinations()
}

sealed class CustomerDestinations : IDestination {

    @Serializable
    data object HomeScreenPath : CustomerDestinations()

    @Serializable
    data object DeliveryScreenPath : CustomerDestinations()
}

sealed class DeliveryDriverDestinations : IDestination {

    @Serializable
    data object HomeScreenPath : DeliveryDriverDestinations()

    @Serializable
    data object DeliveryScreenPath : DeliveryDriverDestinations()

    @Serializable
    data object ScannerScreenPath : DeliveryDriverDestinations()
}