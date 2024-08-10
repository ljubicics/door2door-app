package com.example.door2door_app.delivery.data.repository

import com.example.door2door_app.delivery.data.datasource.DeliveryRemoteDataSource
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.repository.IDeliveryRepository
import com.example.door2door_app.networking.response.RepositoryResponse

class DeliveryRepository(
    private val remoteDataSource: DeliveryRemoteDataSource
) : IDeliveryRepository {
    override suspend fun fetchAllDriverDeliveries(accountId: Long): RepositoryResponse<List<Delivery>> {
        return remoteDataSource.fetchAllDriverDeliveries(accountId)
    }

    override suspend fun fetchAllUserDeliveries(accountId: Long): RepositoryResponse<List<Delivery>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDriverInProgressDelivery(accountId: Long): RepositoryResponse<Delivery> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserInProgressDelivery(accountId: Long): RepositoryResponse<Delivery> {
        TODO("Not yet implemented")
    }
}