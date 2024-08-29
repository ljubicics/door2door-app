package com.example.door2door_app.splash.domain.usecase

import com.example.door2door_app.networking.response.RepositoryResponse
import com.example.door2door_app.user.domain.repository.IUserRepository
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import org.json.JSONObject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class CheckIfUserAlreadyLoggedInUseCase(
    private val preferences: IUserPreferences,
    private val repository: IUserRepository
) {
    suspend operator fun invoke(): Boolean {
        val token = preferences.getToken()

        if (token == "") {
            return false
        }

        if (isTokenExpired(token)) {
            preferences.clearToken()
            return false
        }

        when (val result = repository.fetchUserInfo(preferences.getAccountData().username)) {
            is RepositoryResponse.Success -> {
                preferences.storeUserData(result.body)
            }

            is RepositoryResponse.Error -> {
                preferences.clearToken()
                return false
            }
        }

        return true
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun isTokenExpired(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) {
                true
            } else {
                val payload = String(Base64.decode(parts[1].toByteArray()))
                val jsonObject = JSONObject(payload)
                val exp = jsonObject.optLong("exp", 0)

                exp * 1000 < System.currentTimeMillis()
            }
        } catch (e: Exception) {
            true
        }
    }
}