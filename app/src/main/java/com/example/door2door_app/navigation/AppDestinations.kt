package com.example.door2door_app.navigation

import kotlinx.serialization.Serializable

interface IDestination


@Serializable
object LoginScreenPath : IDestination

@Serializable
object HomeScreenPath : IDestination