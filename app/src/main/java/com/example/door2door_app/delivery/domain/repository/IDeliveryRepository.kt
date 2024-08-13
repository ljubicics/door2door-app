package com.example.door2door_app.delivery.domain.repository

import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.networking.response.RepositoryResponse

interface IDeliveryRepository {
    suspend fun fetchAllDriverDeliveries(accountId: Long): RepositoryResponse<List<Delivery>>
    suspend fun fetchAllUserDeliveries(accountId: Long): RepositoryResponse<List<Delivery>>
    suspend fun fetchDriverInProgressDelivery(accountId: Long): RepositoryResponse<Delivery>
    suspend fun fetchUserInProgressDelivery(accountId: Long): RepositoryResponse<List<Delivery>>
}