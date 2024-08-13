package com.example.door2door_app.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.login.domain.usecase.LoginUseCase
import com.example.door2door_app.login.domain.usecase.StoreAccountInfoUseCase
import com.example.door2door_app.user.domain.model.RoleName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface NextScreen {
    data object Customer : NextScreen
    data object DeliveryDriver : NextScreen
}

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val storeAccountInfoUseCase: StoreAccountInfoUseCase
) : ViewModel() {

    data class State(
        val username: String = "",
        val password: String = ""
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    private val _nextScreen = Channel<NextScreen>()
    val nextScreen = _nextScreen.receiveAsFlow()

    private val _loginError = Channel<Unit>()
    val loginError = _loginError.receiveAsFlow()

    fun onLoginClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase(username(), password())
            if (result.isSuccessfulLogin) {
                when (storeAccountInfoUseCase(username())) {
                    RoleName.ROLE_DELIVERY -> _nextScreen.send(NextScreen.DeliveryDriver)
                    RoleName.ROLE_CUSTOMER -> _nextScreen.send(NextScreen.Customer)
                    RoleName.UNKNOWN -> _loginError.send(Unit)
                    RoleName.ROLE_ADMIN -> _loginError.send(Unit)
                }
            } else {
                _loginError.send(Unit)
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
