package com.example.door2door_app.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.splash.domain.usecase.CheckIfUserAlreadyLoggedInUseCase
import com.example.door2door_app.splash.domain.usecase.CheckSavedUserRoleUseCase
import com.example.door2door_app.user.domain.model.Role
import com.example.door2door_app.user.domain.model.RoleName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkIfUserAlreadyLoggedInUseCase: CheckIfUserAlreadyLoggedInUseCase,
    private val checkSavedUserRoleUseCase: CheckSavedUserRoleUseCase
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

    private fun checkUserRole() {
        viewModelScope.launch(Dispatchers.IO) {
            val account = checkSavedUserRoleUseCase()
            if (account.role == RoleName.ROLE_DELIVERY) {
                _nextScreen.send(NextScreen.DeliveryDriver)
            } else if (account.role == RoleName.ROLE_NORMAL_USER) {
                _nextScreen.send(NextScreen.Customer)
            } else {
                _nextScreen.send(NextScreen.Login)
            }
        }
    }
}

sealed interface NextScreen {
    data object Login : NextScreen
    data object Customer : NextScreen
    data object DeliveryDriver : NextScreen
}
