package com.example.door2door_app.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    data class State(
        val username: String = "",
        val password: String = ""
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    private val _nextScreen = Channel<Unit>()
    val nextScreen = _nextScreen.receiveAsFlow()

    fun onLoginClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase(username(), password())
            if (result.isSuccessfulLogin) {
                _nextScreen.send(Unit)
            } else {
                // show error
            }
        }
    }

    fun setUsername(value: String) {
        _state.update { it.copy(username = value) }
    }

    fun setPassword(value: String) {
        _state.update { it.copy(password = value) }
    }

    private fun username(): String = _state.value.username


    private fun password(): String = _state.value.password
}
