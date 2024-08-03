package com.example.door2door_app.networking.di

import com.example.door2door_app.networking.factory.HttpClientFactory
import org.koin.dsl.module

val networkingModule = module {
    single { HttpClientFactory.getHttpClient() }
}