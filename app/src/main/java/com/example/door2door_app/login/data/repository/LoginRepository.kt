package com.example.door2door_app.login.data.repository

import com.example.door2door_app.login.domain.repository.ILoginRepository
import com.example.door2door_app.login.domain.repository.datasource.IRemoteDataSource
import com.example.door2door_app.login.domain.model.LoginResponse
import com.example.door2door_app.networking.response.RepositoryResponse

class LoginRepository(
    private val remoteDataSource: IRemoteDataSource
) : ILoginRepository {
    override suspend fun login(email: String, password: String): RepositoryResponse<LoginResponse> {
        return remoteDataSource.login(email = email, password = password)
    }
}