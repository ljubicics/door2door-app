package com.example.door2door_app.login.domain.usecase

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.Role
import com.example.door2door_app.user.domain.model.RoleName
import com.example.door2door_app.user.domain.repository.IUserRepository
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class StoreAccountInfoUseCase(
    private val preferences: IUserPreferences,
    private val userRepository: IUserRepository,
) {

    suspend operator fun invoke(username: String): RoleName {
        val account = when (val result = userRepository.fetchAccountInfo(username = username)) {
            is RepositoryResponse.Success -> {
                try {
                    Account(
                        id = result.body.id,
                        username = result.body.username,
                        role = result.body.role.name
                    )
                } catch (e: Exception) {
                    Account(
                        role = RoleName.UNKNOWN
                    )
                }
            }

            is RepositoryResponse.Error -> Account(
                role = RoleName.UNKNOWN
            )
        }

        preferences.storeAccountData(account = account)
        return account.role
    }

}