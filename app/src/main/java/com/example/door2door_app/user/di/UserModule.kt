package com.example.door2door_app.user.di

import com.example.door2door_app.user.data.repository.UserPreferences
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import org.koin.dsl.module

val userModule = module {

    single<IUserPreferences> { UserPreferences(get()) }
}