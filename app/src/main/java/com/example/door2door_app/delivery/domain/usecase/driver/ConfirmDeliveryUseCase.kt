package com.example.door2door_app.delivery.domain.usecase.driver

import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse

class ConfirmDeliveryUseCase(
    private val repository: IDeliveryRepository
) {
    suspend operator fun invoke(confirmPath: String): Boolean {
        return when (repository.confirmDelivery(
            confirmPath = confirmPath
        )) {
            is RepositoryResponse.Error -> false
            is RepositoryResponse.Success -> true
        }
    }
}