package com.example.door2door_app.delivery.ui.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.delivery.domain.usecase.driver.AcceptDeliveryUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.ChangeDeliveryStatusUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.GetAllFinishedDriverDeliveriesUseCase
import com.example.door2door_app.delivery.domain.usecase.driver.GetInProgressDriverDeliveryUseCase
import com.example.door2door_app.user.domain.model.Account
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

class DriverDeliveriesViewModel(
    private val getAllFinishedDriverDeliveriesUseCase: GetAllFinishedDriverDeliveriesUseCase,
    private val getInProgressDriverDeliveryUseCase: GetInProgressDriverDeliveryUseCase,
    private val changeDeliveryStatusUseCase: ChangeDeliveryStatusUseCase,
    private val acceptDeliveryUseCase: AcceptDeliveryUseCase,
    private val preferences: IUserPreferences
) : ViewModel() {

    data class State(
        val account: Account? = null,
        val user: User? = null,
        val finishedDeliveries: List<Delivery> = emptyList(),
        val inProgressDelivery: Delivery? = null,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    private val _onNavigationButtonClick = Channel<Unit>()
    val onNavigationButtonClick = _onNavigationButtonClick.receiveAsFlow()

    private val _openScanner = Channel<Unit>()
    val openScanner = _openScanner.receiveAsFlow()

    fun loadScreenInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(isLoading = true)
            loadDriverInfo()
            loadDeliveries()
            setIsLoading(isLoading = false)
        }
    }

    fun onNavigationButtonClick() {
        viewModelScope.launch {
            _onNavigationButtonClick.send(Unit)
        }
    }

    fun onDeliveryStatusButtonClick() {
        viewModelScope.launch {
            resolveStatusButtonClick()
        }
    }

    fun acceptDelivery(deliveryId: Long) {
        viewModelScope.launch {
            setIsLoading(isLoading = true)
            val result = acceptDeliveryUseCase(
                deliveryId = deliveryId
            )
            if (result) {
                loadDeliveries()
            }
            setIsLoading(isLoading = false)
        }
    }

    private suspend fun loadDeliveries() {
        val deliveries = getAllFinishedDriverDeliveriesUseCase()
        val inProgressDelivery = getInProgressDriverDeliveryUseCase()
        _state.update { it.copy(finishedDeliveries = deliveries, inProgressDelivery = inProgressDelivery) }
    }

    private suspend fun loadDriverInfo() {
        val account = preferences.getAccountData()
        val user = preferences.getUserData()
        _state.update { it.copy(account = account, user = user) }
    }

    private suspend fun resolveStatusButtonClick() {
        inProgressDelivery()?.let {
            when (it.status) {
                DeliveryStatus.ACCEPTED -> changeDeliveryStatus()
                DeliveryStatus.IN_PROGRESS -> _openScanner.send(Unit)
                else -> return
            }
        }
    }

    fun changeDeliveryStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val deliveryId = inProgressDelivery()?.id ?: 0L
            val result = when (inProgressDelivery()?.status) {
                DeliveryStatus.ACCEPTED -> {
                    changeInProgressDeliveryStatus()
                    changeDeliveryStatusUseCase(
                        deliveryId = deliveryId,
                        DeliveryStatus.IN_PROGRESS
                    )
                }

                else -> return@launch
            }
        }
    }

    private fun changeInProgressDeliveryStatus() {
        val changedStatusDelivery = inProgressDelivery()?.copy(status = DeliveryStatus.IN_PROGRESS)
        _state.update { it.copy(inProgressDelivery = changedStatusDelivery) }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun deliveries() = _state.value.finishedDeliveries

    private fun inProgressDelivery() = _state.value.inProgressDelivery
}