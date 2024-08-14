package com.example.door2door_app.login.domain.usecase

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.repository.IUserRepository
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class StoreUserInfoUseCase(
    private val userRepository: IUserRepository,
    private val preferences: IUserPreferences
) {

    suspend operator fun invoke(username: String) {
        when (val result = userRepository.fetchUserInfo(username = username)) {
            is RepositoryResponse.Success -> {
                preferences.storeUserData(user = result.body)
            }

            is RepositoryResponse.Error -> {
                // Handle error
            }
        }
    }
}