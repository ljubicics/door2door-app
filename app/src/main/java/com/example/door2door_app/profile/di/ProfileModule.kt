package com.example.door2door_app.profile.di

import com.example.door2door_app.profile.domain.usecase.FetchAccountInfoUseCase
import com.example.door2door_app.profile.ui.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)

    single { FetchAccountInfoUseCase(get(), get()) }
}