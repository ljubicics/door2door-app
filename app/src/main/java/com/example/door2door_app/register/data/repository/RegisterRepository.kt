package com.example.door2door_app.register.data.repository

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.networking.util.safeRequest
import com.example.door2door_app.register.domain.model.request.RegisterRequest
import com.example.door2door_app.register.domain.repository.IRegisterRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class RegisterRepository(
    private val httpClient: HttpClient
) : IRegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): RepositoryResponse<Any> {
        return httpClient.safeRequest {
            url(path = "v1/auth/register") {
                method = HttpMethod.Post
                setBody(registerRequest)
            }
        }
    }
}