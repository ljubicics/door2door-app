package com.example.door2door_app.profile.domain.usecase

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.RoleName
import com.example.door2door_app.user.domain.repository.IUserRepository
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class FetchAccountInfoUseCase(
    private val repository: IUserRepository,
    private val preferences: IUserPreferences
) {

    suspend operator fun invoke(username: String): Account {
        val account = when(val result = repository.fetchAccountInfo(username = username)) {
            is RepositoryResponse.Success -> {
                try {
                    Account(
                        id = result.body.id,
                        username = result.body.username,
                        role = result.body.role.name,
                        numberOfDeliveries = result.body.numberOfDeliveries
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

        preferences.updateAccountNumberOfDeliveries(account = account)
        return account
    }
}