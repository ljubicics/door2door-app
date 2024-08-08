package com.example.door2door_app.user.domain.repository.datasource

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.data.model.AccountDto
import com.example.door2door_app.user.data.model.UserDto

interface IUserRemoteDataSource {
    suspend fun fetchUserRole(username: String): RepositoryResponse<UserDto>
    suspend fun fetchAccountInfo(username: String): RepositoryResponse<AccountDto>
}