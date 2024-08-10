package com.example.door2door_app.delivery.data.datasource

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
        val response = httpClient.safeRequest<List<Delivery>> {
            url("v1/deliveries/driver?id=$driverId")
            method = HttpMethod.Get
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(response.body)
        }
    }
}