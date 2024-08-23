package com.example.door2door_app.delivery.di

import com.example.door2door_app.delivery.data.datasource.DeliveryRemoteDataSource
import com.example.door2door_app.delivery.data.repository.DeliveryRepository
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.delivery.domain.usecase.customer.GetAllCustomerActiveDeliveriesUseCase
import com.example.door2door_app.delivery.domain.usecase.customer.GetAllCustomerFinishedDeliveriesUseCase
import com.example.door2door_app.delivery.domain.usecase.details.GetDeliveryDetailsUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.AcceptDeliveryUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.ChangeDeliveryStatusUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.ConfirmDeliveryUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.GetAllFinishedDriverDeliveriesUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.GetInProgressDriverDeliveryUseCase
import com.example.door2door_app.delivery.ui.customer.CustomerDeliveriesViewModel
import com.example.door2door_app.delivery.ui.details.DeliveryDetailsViewModel
import com.example.door2door_app.delivery.ui.driver.DriverDeliveriesViewModel
import com.example.door2door_app.delivery.ui.driver.scanner.ScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val deliveryModule = module {
    viewModelOf(::DriverDeliveriesViewModel)
    viewModelOf(::CustomerDeliveriesViewModel)
    viewModelOf(::DeliveryDetailsViewModel)
    viewModelOf(::ScannerViewModel)

    single { DeliveryRemoteDataSource(get()) }
    single<IDeliveryRepository> { DeliveryRepository(get()) }

    // usecase
    single { GetAllFinishedDriverDeliveriesUseCase(get(), get()) }
    single { GetInProgressDriverDeliveryUseCase(get(), get()) }
    single { ChangeDeliveryStatusUseCase(get()) }
    single { GetAllCustomerFinishedDeliveriesUseCase(get(), get()) }
    single { GetAllCustomerActiveDeliveriesUseCase(get(), get()) }
    single { GetDeliveryDetailsUseCase(get()) }
    single { ConfirmDeliveryUseCase(get()) }
    single { AcceptDeliveryUseCase(get(), get()) }
}