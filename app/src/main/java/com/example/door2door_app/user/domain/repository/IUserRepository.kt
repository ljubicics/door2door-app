package com.example.door2door_app.user.domain.repository

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.data.model.AccountDto
import com.example.door2door_app.user.data.model.UserDto
import com.example.door2door_app.user.domain.model.User

interface IUserRepository {
    suspend fun fetchUserRole(username: String): RepositoryResponse<UserDto>
    suspend fun fetchUserInfo(username: String): RepositoryResponse<User>
    suspend fun fetchAccountInfo(username: String): RepositoryResponse<AccountDto>
}