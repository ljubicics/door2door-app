package com.example.door2door_app.user.data.datasource.remote

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.networking.util.safeRequest
import com.example.door2door_app.user.data.model.AccountDto
import com.example.door2door_app.user.data.model.UserDto
import com.example.door2door_app.user.data.repository.UserMapper
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.user.domain.repository.datasource.IUserRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class UserRemoteDataSource(
    private val httpClient: HttpClient
) : IUserRemoteDataSource {
    override suspend fun fetchUserRole(username: String): RepositoryResponse<UserDto> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserInfo(username: String): RepositoryResponse<User> {
        val response = httpClient.safeRequest<UserDto> {
            url(path = "v1/user/info?username=$username") {
                method = HttpMethod.Get
            }
        }

        return when (response) {
            is RepositoryResponse.Error -> response
            is RepositoryResponse.Success -> RepositoryResponse.Success(UserMapper.map(response.body))
        }
    }

    override suspend fun fetchAccountInfo(username: String): RepositoryResponse<AccountDto> {
        return httpClient.safeRequest {
            url(path = "v1/accounts/getAccountInfo?username=$username") {
                method = HttpMethod.Get
            }
        }
    }
}