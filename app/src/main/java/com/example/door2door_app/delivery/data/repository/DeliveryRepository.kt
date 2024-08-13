package com.example.door2door_app.delivery.data.repository

import com.example.door2door_app.delivery.data.datasource.DeliveryRemoteDataSource
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse

class DeliveryRepository(
    private val remoteDataSource: DeliveryRemoteDataSource
) : IDeliveryRepository {
    override suspend fun fetchAllDriverDeliveries(accountId: Long): RepositoryResponse<List<Delivery>> {
        return remoteDataSource.fetchAllDriverDeliveries(driverId = accountId)
    }

    override suspend fun fetchAllUserDeliveries(accountId: Long): RepositoryResponse<List<Delivery>> {
        return remoteDataSource.fetchAllUserDeliveries(userId = accountId)
    }

    override suspend fun fetchDriverInProgressDelivery(accountId: Long): RepositoryResponse<Delivery> {
        return remoteDataSource.fetchAllDriverInProgressDeliveries(driverId = accountId)
    }

    override suspend fun fetchUserInProgressDelivery(accountId: Long): RepositoryResponse<List<Delivery>> {
        return remoteDataSource.fetchAllUserInProgressDeliveries(userId = accountId)
    }

    override suspend fun changeDeliveryStatus(deliveryId: Long, status: DeliveryStatus): RepositoryResponse<Boolean> {
        return remoteDataSource.changeDeliveryStatus(deliveryId = deliveryId, status = status.toString())
    }
}