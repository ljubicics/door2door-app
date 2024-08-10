package com.example.door2door_app.login.di

import com.example.door2door_app.login.data.datasource.RemoteDataSource
import com.example.door2door_app.login.data.repository.LoginRepository
import com.example.door2door_app.login.domain.repository.ILoginRepository
import com.example.door2door_app.login.domain.repository.datasource.IRemoteDataSource
import com.example.door2door_app.login.domain.usecase.LoginUseCase
import com.example.door2door_app.login.domain.usecase.StoreAccountInfoUseCase
import com.example.door2door_app.login.ui.LoginViewModel
import com.example.door2door_app.navigation.bottom.BottomNavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {

    //viewmodel
    viewModelOf(::LoginViewModel)
    viewModelOf(::BottomNavigationViewModel)

    single<IRemoteDataSource> { RemoteDataSource(get()) }
    single<ILoginRepository> { LoginRepository(get()) }

    //usecase
    single { LoginUseCase(get(), get()) }
    single { StoreAccountInfoUseCase(get(), get()) }
}