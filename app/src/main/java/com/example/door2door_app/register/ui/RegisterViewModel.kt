package com.example.door2door_app.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.register.domain.model.request.RegisterRequest
import com.example.door2door_app.register.domain.usecase.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    data class State(
        val username: String = "",
        val password: String = "",
        val name: String = "",
        val surname: String = "",
        val email: String = "",
        val phoneNumber: String = "",
        val address: String = ""
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    private val _nextScreen = Channel<Unit>()
    val nextScreen = _nextScreen.receiveAsFlow()

    private val _registerError = Channel<Unit>()
    val registerError = _registerError.receiveAsFlow()

    fun register() {
        val registerRequest = RegisterRequest(
            username = username(),
            password = password(),
            name = name(),
            surname = surname(),
            email = email(),
            mobileNumber = phoneNumber(),
            address = address()
        )

        viewModelScope.launch(Dispatchers.IO) {
            val result = registerUseCase(registerRequest)
            if (result.isSuccessfulRegister) {
                _nextScreen.send(Unit)
            } else {
                _registerError.send(Unit)
            }
        }
    }

    fun setUsername(value: String) {
        _state.update { it.copy(username = value) }
    }

    fun setPassword(value: String) {
        _state.update { it.copy(password = value) }
    }

    fun setName(value: String) {
        _state.update { it.copy(name = value) }
    }

    fun setSurname(value: String) {
        _state.update { it.copy(surname = value) }
    }

    fun setEmail(value: String) {
        _state.update { it.copy(email = value) }
    }

    fun setPhoneNumber(value: String) {
        _state.update { it.copy(phoneNumber = value) }
    }

    fun setAddress(value: String) {
        _state.update { it.copy(address = value) }
    }

    private fun username() = _state.value.username

    private fun password() = _state.value.password

    private fun name() = _state.value.name

    private fun surname() = _state.value.surname

    private fun email() = _state.value.email

    private fun phoneNumber() = _state.value.phoneNumber

    private fun address() = _state.value.address
}