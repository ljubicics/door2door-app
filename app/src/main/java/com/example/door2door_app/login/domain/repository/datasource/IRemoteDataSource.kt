package com.example.door2door_app.login.domain.repository.datasource

import com.example.door2door_app.login.domain.model.LoginResponse
import com.example.door2door_app.networking.response.RepositoryResponse

interface IRemoteDataSource {

    suspend fun login(email: String, password: String): RepositoryResponse<LoginResponse>
}