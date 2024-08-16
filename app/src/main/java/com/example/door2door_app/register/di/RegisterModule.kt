package com.example.door2door_app.register.di

import com.example.door2door_app.register.data.repository.RegisterRepository
import com.example.door2door_app.register.domain.repository.IRegisterRepository
import com.example.door2door_app.register.domain.usecase.RegisterUseCase
import com.example.door2door_app.register.ui.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val registerModule = module {
    viewModelOf(::RegisterViewModel)

    single<IRegisterRepository> { RegisterRepository(get()) }
    single { RegisterUseCase(get()) }
}