package com.example.door2door_app.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.splash.domain.usecase.CheckIfUserAlreadyLoggedInUseCase
import com.example.door2door_app.splash.domain.usecase.CheckSavedUserRoleUseCase
import com.example.door2door_app.user.domain.model.RoleName
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface NextScreen {
    data object Login : NextScreen
    data object Customer : NextScreen
    data object DeliveryDriver : NextScreen
}

class SplashViewModel(
    private val checkIfUserAlreadyLoggedInUseCase: CheckIfUserAlreadyLoggedInUseCase,
    private val checkSavedUserRoleUseCase: CheckSavedUserRoleUseCase,
    private val preferences: IUserPreferences
) : ViewModel() {

    private val _nextScreen = Channel<NextScreen>()
    val nextScreen = _nextScreen.receiveAsFlow()

    fun checkIfUserLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            val isLoggedIn = checkIfUserAlreadyLoggedInUseCase()
            if (isLoggedIn) {
                checkUserRole()
            } else {
                _nextScreen.send(NextScreen.Login)
            }
        }
    }

    private suspend fun checkUserRole() {
        val account = checkSavedUserRoleUseCase()
        when (account.role) {
            RoleName.ROLE_DELIVERY -> _nextScreen.send(NextScreen.DeliveryDriver)
            RoleName.ROLE_CUSTOMER -> _nextScreen.send(NextScreen.Customer)
            RoleName.UNKNOWN -> {
                preferences.clearToken()
                _nextScreen.send(NextScreen.Login)
            }
        }
    }
}
