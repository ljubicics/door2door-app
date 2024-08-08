package com.example.door2door_app.splash.di

import com.example.door2door_app.splash.domain.usecase.CheckIfUserAlreadyLoggedInUseCase
import com.example.door2door_app.splash.domain.usecase.CheckSavedUserRoleUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.door2door_app.splash.ui.SplashViewModel
import org.koin.dsl.module

val splashModule = module {
    viewModelOf(::SplashViewModel)

    single { CheckIfUserAlreadyLoggedInUseCase(get()) }
    single { CheckSavedUserRoleUseCase(get()) }
}