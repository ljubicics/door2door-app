package com.example.door2door_app.delivery.ui.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.usecase.customer.GetAllCustomerActiveDeliveriesUseCase
import com.example.door2door_app.delivery.domain.usecase.customer.GetAllCustomerFinishedDeliveriesUseCase
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomerDeliveriesViewModel(
    private val preferences: IUserPreferences,
    private val getAllCustomerFinishedDeliveriesUseCase: GetAllCustomerFinishedDeliveriesUseCase,
    private val getAllCustomerActiveDeliveriesUseCase: GetAllCustomerActiveDeliveriesUseCase
) : ViewModel() {

    init {
        loadScreenInfo()
    }

    data class State(
        val account: Account? = null,
        val user: User? = null,
        val finishedDeliveries: List<Delivery> = emptyList(),
        val activeDeliveries: List<Delivery> = emptyList(),
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), State()
    )

    private fun loadScreenInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(isLoading = true)
            val account = preferences.getAccountData()
            val user = preferences.getUserData()
            val activeDeliveries = getAllCustomerActiveDeliveriesUseCase()
            val finishedDeliveries = getAllCustomerFinishedDeliveriesUseCase()
            delay(1000)
            updateState(
                account = account,
                user = user,
                activeDeliveries = activeDeliveries,
                finishedDeliveries = finishedDeliveries
            )
        }
    }

    private fun updateState(
        account: Account,
        user: User,
        activeDeliveries: List<Delivery>,
        finishedDeliveries: List<Delivery>
    ) {
        _state.update {
            it.copy(
                account = account,
                user = user,
                activeDeliveries = activeDeliveries,
                finishedDeliveries = finishedDeliveries,
                isLoading = false
            )
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}