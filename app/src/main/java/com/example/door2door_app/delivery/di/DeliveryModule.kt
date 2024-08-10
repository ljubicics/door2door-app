package com.example.door2door_app.delivery.di

import com.example.door2door_app.delivery.data.datasource.DeliveryRemoteDataSource
import com.example.door2door_app.delivery.data.repository.DeliveryRepository
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.delivery.domain.usecase.GetAllFinishedDriverDeliveriesUseCase
import com.example.door2door_app.delivery.ui.driver.DriverDeliveriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val deliveryModule = module {
    viewModelOf(::DriverDeliveriesViewModel)

    single { DeliveryRemoteDataSource(get()) }
    single<IDeliveryRepository> { DeliveryRepository(get()) }

    // usecase
    single { GetAllFinishedDriverDeliveriesUseCase(get(), get()) }
}