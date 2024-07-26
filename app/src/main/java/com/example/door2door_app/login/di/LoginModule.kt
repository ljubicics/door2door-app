package com.example.door2door_app.login.di

import com.example.door2door_app.login.data.repository.LoginRepository
import com.example.door2door_app.login.data.repository.datasource.RemoteDataSource
import com.example.door2door_app.login.domain.repository.ILoginRepository
import com.example.door2door_app.login.domain.repository.datasource.IRemoteDataSource
import com.example.door2door_app.login.domain.usecase.LoginUseCase
import com.example.door2door_app.login.ui.LoginViewModel
import com.example.door2door_app.networking.factory.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {

    //viewmodel
    viewModelOf(::LoginViewModel)

    single<HttpClient> { HttpClientFactory.getHttpClient() }

    single<IRemoteDataSource> { RemoteDataSource(get()) }
    single<ILoginRepository> { LoginRepository(get()) }

    //usecase
    single { LoginUseCase(get()) }
}