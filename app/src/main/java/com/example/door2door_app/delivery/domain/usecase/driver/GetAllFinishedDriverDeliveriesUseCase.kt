package com.example.door2door_app.delivery.domain.usecase.driver

import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class GetAllFinishedDriverDeliveriesUseCase(
    private val deliveryRepository: IDeliveryRepository,
    private val preferences: IUserPreferences
) {
    suspend operator fun invoke(): List<Delivery> {
        val driverId = preferences.getAccountData().id
        return when (val result = deliveryRepository.fetchAllDriverDeliveries(driverId)) {
            is RepositoryResponse.Error -> emptyList()
            is RepositoryResponse.Success -> result.body
        }
    }
}