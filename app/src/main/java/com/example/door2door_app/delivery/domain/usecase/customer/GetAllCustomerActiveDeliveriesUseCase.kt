package com.example.door2door_app.delivery.domain.usecase.customer

import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class GetAllCustomerActiveDeliveriesUseCase(
    private val repository: IDeliveryRepository,
    private val preferences: IUserPreferences
) {

    suspend operator fun invoke(): List<Delivery> {
        val customerId = preferences.getAccountData().id
        return when (val result = repository.fetchAllUserActiveDeliveries(accountId = customerId)) {
            is RepositoryResponse.Error -> emptyList()
            is RepositoryResponse.Success -> result.body
        }
    }
}