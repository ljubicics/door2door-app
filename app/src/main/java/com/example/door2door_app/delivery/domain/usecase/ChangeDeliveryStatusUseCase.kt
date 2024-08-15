package com.example.door2door_app.delivery.domain.usecase

import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse

class ChangeDeliveryStatusUseCase(
    private val deliveryRepository: IDeliveryRepository,
) {

    suspend operator fun invoke(deliveryId: Long, status: DeliveryStatus): Boolean {
        return when (deliveryRepository.changeDeliveryStatus(deliveryId = deliveryId, status = status)) {
            is RepositoryResponse.Error -> false
            is RepositoryResponse.Success -> true
        }
    }
}