package com.example.door2door_app.user.di

import com.example.door2door_app.user.data.datasource.remote.UserRemoteDataSource
import com.example.door2door_app.user.data.preferences.UserPreferences
import com.example.door2door_app.user.data.repository.UserRepository
import com.example.door2door_app.user.domain.repository.IUserRepository
import com.example.door2door_app.user.domain.repository.datasource.IUserRemoteDataSource
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import org.koin.dsl.module

val userModule = module {

    single<IUserPreferences> { UserPreferences(get()) }
    single<IUserRemoteDataSource> { UserRemoteDataSource(get()) }
    single<IUserRepository> { UserRepository(get()) }
}