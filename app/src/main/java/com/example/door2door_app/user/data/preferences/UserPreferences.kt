package com.example.door2door_app.user.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.RoleName
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPreferences(
    private val preferences: DataStore<Preferences>
) : IUserPreferences {

    private val tokenKey = stringPreferencesKey("TOKEN")
    private val accountInfoKey = stringPreferencesKey("ACCOUNT_INFO")
    private val userInfoKey = stringPreferencesKey("USER_INFO")

    override suspend fun storeToken(token: String) {
        preferences.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun clearToken() {
        preferences.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }

    override suspend fun getToken(): String {
        return preferences.data.map { preferences ->
            preferences[tokenKey] ?: ""
        }.first()
    }

    override suspend fun storeUserData(user: User) {
        preferences.edit { preferences ->
            preferences[userInfoKey] = Json.encodeToString(user)
        }
    }

    override suspend fun getUserData(): User {
        val user = preferences.data.map { preferences ->
            preferences[userInfoKey] ?: ""
        }.first()
        return Json.decodeFromString(string = user)
    }

    override suspend fun storeAccountData(account: Account) {
        preferences.edit { preferences ->
            preferences[accountInfoKey] = Json.encodeToString(account)
        }
    }

    override suspend fun getAccountData(): Account {
        val account = preferences.data.map { preferences ->
            preferences[accountInfoKey] ?: ""
        }.first()

        return if (account != "") {
            Json.decodeFromString(string = account)
        } else {
            Account(
                role = RoleName.UNKNOWN
            )
        }
    }
}