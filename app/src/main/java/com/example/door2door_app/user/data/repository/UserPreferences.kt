package com.example.door2door_app.user.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val preferences: DataStore<Preferences>
) : IUserPreferences {

    private val tokenKey = stringPreferencesKey("TOKEN")

    override suspend fun storeToken(token: String) {
        preferences.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun getToken(): String {
        return preferences.data.map { preferences ->
            preferences[tokenKey] ?: ""
        }.first()
    }

    override suspend fun storeUserData(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): User {
        TODO("Not yet implemented")
    }
}