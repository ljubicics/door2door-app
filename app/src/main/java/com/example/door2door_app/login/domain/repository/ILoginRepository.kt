package com.example.door2door_app.login.domain.repository

import com.example.door2door_app.login.domain.model.LoginResponse
import com.example.door2door_app.networking.response.RepositoryResponse

interface ILoginRepository {
    suspend fun login(email: String, password: String): RepositoryResponse<LoginResponse>
}