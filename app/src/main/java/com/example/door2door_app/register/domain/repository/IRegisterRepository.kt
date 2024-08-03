package com.example.door2door_app.register.domain.repository

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.register.domain.model.request.RegisterRequest

interface IRegisterRepository {
    suspend fun register(registerRequest: RegisterRequest): RepositoryResponse<Any>
}