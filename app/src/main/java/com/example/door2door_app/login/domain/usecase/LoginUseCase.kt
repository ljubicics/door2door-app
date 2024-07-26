package com.example.door2door_app.login.domain.usecase

import com.example.door2door_app.login.domain.repository.ILoginRepository
import com.example.door2door_app.login.model.LoginResult
import com.example.door2door_app.networking.response.RepositoryResponse

class LoginUseCase(
    private val repository: ILoginRepository,
) {

    suspend operator fun invoke(email: String, password: String): LoginResult {
        return when (val result = repository.login(email, password)) {
            is RepositoryResponse.Success -> {
//                preferences.storeToken(result.body.token)
                LoginResult(
                    isSuccessfulLogin = true
                )
            }

            is RepositoryResponse.Error -> {
                LoginResult(
                    isSuccessfulLogin = false
                )
            }
        }
    }
}