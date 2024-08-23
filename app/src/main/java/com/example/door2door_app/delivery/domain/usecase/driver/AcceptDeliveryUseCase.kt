package com.example.door2door_app.delivery.domain.usecase.driver

import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class AcceptDeliveryUseCase(
    private val repository: IDeliveryRepository,
    private val preferences: IUserPreferences
) {
    suspend operator fun invoke(deliveryId: Long): Boolean {
        return when (repository.acceptDelivery(deliveryId = deliveryId, driverId = preferences.getAccountData().id)) {
            is RepositoryResponse.Error -> false
            is RepositoryResponse.Success -> true
        }
    }
}