package com.example.door2door_app.register.domain.usecase

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.register.domain.model.RegisterResult
import com.example.door2door_app.register.domain.model.request.RegisterRequest
import com.example.door2door_app.register.domain.repository.IRegisterRepository

class RegisterUseCase(
    private val registerRepository: IRegisterRepository
) {

    suspend operator fun invoke(registerRequest: RegisterRequest): RegisterResult {
        return when(registerRepository.register(registerRequest)) {
            is RepositoryResponse.Success -> {
                RegisterResult(
                    isSuccessfulRegister = true
                )
            }
            is RepositoryResponse.Error -> {
                RegisterResult(
                    isSuccessfulRegister = false
                )
            }
        }
    }
}