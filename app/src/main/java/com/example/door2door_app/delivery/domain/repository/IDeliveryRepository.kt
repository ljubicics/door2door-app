package com.example.door2door_app.delivery.domain.repository

import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.networking.response.RepositoryResponse

interface IDeliveryRepository {
    suspend fun fetchAllDriverDeliveries(accountId: Long): RepositoryResponse<List<Delivery>>
    suspend fun fetchAllUserFinishedDeliveries(accountId: Long): RepositoryResponse<List<Delivery>>
    suspend fun fetchDriverInProgressDelivery(accountId: Long): RepositoryResponse<Delivery>
    suspend fun fetchAllUserActiveDeliveries(accountId: Long): RepositoryResponse<List<Delivery>>
    suspend fun changeDeliveryStatus(deliveryId: Long, status: DeliveryStatus): RepositoryResponse<Boolean>
    suspend fun getDeliveryDetails(deliveryId: Long): RepositoryResponse<Delivery>
    suspend fun confirmDelivery(confirmPath: String): RepositoryResponse<Boolean>
}