package com.example.door2door_app.user.data.repository

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.data.model.AccountDto
import com.example.door2door_app.user.data.model.UserDto
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.user.domain.repository.IUserRepository
import com.example.door2door_app.user.domain.repository.datasource.IUserRemoteDataSource

class UserRepository(
    private val userRemoteDataSource: IUserRemoteDataSource
) : IUserRepository {
    override suspend fun fetchUserRole(username: String): RepositoryResponse<UserDto> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserInfo(username: String): RepositoryResponse<User> {
        return userRemoteDataSource.fetchUserInfo(username)
    }

    override suspend fun fetchAccountInfo(username: String): RepositoryResponse<AccountDto> {
        return userRemoteDataSource.fetchAccountInfo(username)
    }
}