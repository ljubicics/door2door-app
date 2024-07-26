package com.example.door2door_app.login.data.repository.datasource

import com.example.door2door_app.login.domain.repository.datasource.IRemoteDataSource
import com.example.door2door_app.login.model.LoginRequest
import com.example.door2door_app.login.model.LoginResponse
import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.networking.util.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class RemoteDataSource(
    private val httpClient: HttpClient
) : IRemoteDataSource {
    override suspend fun login(email: String, password: String): RepositoryResponse<LoginResponse> {
        return httpClient.safeRequest {
            url(path = "v1/auth/login") {
                method = HttpMethod.Post
                setBody(LoginRequest(email, password))
            }
        }
    }

}