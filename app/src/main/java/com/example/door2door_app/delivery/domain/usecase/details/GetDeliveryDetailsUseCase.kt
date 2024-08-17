package com.example.door2door_app.delivery.domain.usecase.details

import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse

class GetDeliveryDetailsUseCase(
    private val repository: IDeliveryRepository
) {
    suspend operator fun invoke(deliveryId: Long): Delivery? {
        return when(val result = repository.getDeliveryDetails(deliveryId)) {
            is RepositoryResponse.Error -> null
            is RepositoryResponse.Success -> result.body
        }
    }
}