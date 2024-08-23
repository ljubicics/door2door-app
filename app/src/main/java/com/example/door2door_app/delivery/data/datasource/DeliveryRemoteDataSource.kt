package com.example.door2door_app.delivery.data.datasource

import com.example.door2door_app.delivery.data.dto.DeliveryDto
import com.example.door2door_app.delivery.data.repository.mapper.DeliveryMapper
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.networking.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class DeliveryRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun fetchAllDriverDeliveries(driverId: Long): RepositoryResponse<List<Delivery>> {
        val response = httpClient.safeRequest<List<DeliveryDto>> {
            url("v1/deliveries/driver?id=$driverId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(response.body.map { DeliveryMapper.map(it) })
        }
    }

    suspend fun fetchAllDriverInProgressDeliveries(driverId: Long): RepositoryResponse<Delivery> {
        val response = httpClient.safeRequest<DeliveryDto> {
            url("v1/deliveries/driver/inProgress?id=$driverId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(DeliveryMapper.map(response.body))
        }
    }

    suspend fun fetchAllUserDeliveries(userId: Long): RepositoryResponse<List<Delivery>> {
        val response = httpClient.safeRequest<List<DeliveryDto>> {
            url("v1/deliveries/customer?id=$userId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(DeliveryMapper.map(response.body))
        }
    }

    suspend fun fetchAllUserInProgressDeliveries(userId: Long): RepositoryResponse<List<Delivery>> {
        val response = httpClient.safeRequest<List<DeliveryDto>> {
            url("v1/deliveries/customer/inProgress?id=$userId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(DeliveryMapper.map(response.body))
        }
    }

    suspend fun changeDeliveryStatus(deliveryId: Long, status: String): RepositoryResponse<Boolean> {
        val response = httpClient.safeRequest<Boolean> {
            url("v1/deliveries/changeStatus?id=$deliveryId&status=$status")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(response.body)
        }
    }

    suspend fun fetchDeliveryDetails(deliveryId: Long): RepositoryResponse<Delivery> {
        val response = httpClient.safeRequest<DeliveryDto> {
            url("v1/deliveries/info?id=$deliveryId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(DeliveryMapper.map(response.body))
        }
    }

    suspend fun confirmDelivery(confirmPath: String): RepositoryResponse<Boolean> {
        val response = httpClient.safeRequest<Boolean> {
            url("v1/deliveries/confirm?$confirmPath")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(response.body)
        }
    }

    suspend fun acceptDelivery(driverId: Long, deliveryId: Long): RepositoryResponse<Boolean> {
        val response = httpClient.safeRequest<Unit> {
            url("v1/deliveries/acceptDelivery?deliveryId=$deliveryId&driverId=$driverId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(true)
        }
    }
}