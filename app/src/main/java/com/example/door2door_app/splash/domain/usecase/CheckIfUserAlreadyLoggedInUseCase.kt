package com.example.door2door_app.splash.domain.usecase

import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class CheckIfUserAlreadyLoggedInUseCase(
    private val preferences: IUserPreferences
) {
    suspend operator fun invoke(): Boolean {
        val token = preferences.getToken()

        return token != ""
    }
}