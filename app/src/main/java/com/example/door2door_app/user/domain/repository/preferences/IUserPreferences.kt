package com.example.door2door_app.user.domain.repository.preferences

import com.example.door2door_app.user.domain.model.User

interface IUserPreferences {
    suspend fun storeToken(token: String)
    suspend fun getToken(): String
    suspend fun storeUserData(user: User)
    suspend fun getUserData(): User
}