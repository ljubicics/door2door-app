package com.example.door2door_app.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val preferences: IUserPreferences
) : ViewModel() {

    data class State(
        val user: User? = null,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    private val _logout = Channel<Unit>()
    val logout = _logout.receiveAsFlow()

    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(isLoading = true)
            val user = preferences.getUserData()
            _state.value = State(user = user)
            setIsLoading(isLoading = false)
        }
    }

    fun onLogOutClick() {
        viewModelScope.launch(Dispatchers.IO) {
            preferences.clearToken()
            preferences.clearUserData()
            preferences.clearAccountData()
            _logout.send(Unit)
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}