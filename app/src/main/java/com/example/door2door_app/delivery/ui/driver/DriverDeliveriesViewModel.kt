package com.example.door2door_app.delivery.ui.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.usecase.GetAllFinishedDriverDeliveriesUseCase
import com.example.door2door_app.delivery.domain.usecase.GetInProgressDriverDeliveryUseCase
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverDeliveriesViewModel(
    private val getAllFinishedDriverDeliveriesUseCase: GetAllFinishedDriverDeliveriesUseCase,
    private val getInProgressDriverDeliveryUseCase: GetInProgressDriverDeliveryUseCase,
    private val preferences: IUserPreferences
) : ViewModel() {

    data class State(
        val account: Account? = null,
        val finishedDeliveries: List<Delivery> = emptyList(),
        val inProgressDelivery: Delivery? = null
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    fun loadDeliveries() {
        viewModelScope.launch(Dispatchers.IO) {
            val deliveries = getAllFinishedDriverDeliveriesUseCase()
            val inProgressDelivery = getInProgressDriverDeliveryUseCase()
            _state.update { it.copy(finishedDeliveries = deliveries, inProgressDelivery = inProgressDelivery) }
        }
    }

    fun loadDriverInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val account = preferences.getAccountData()
            _state.update { it.copy(account = account) }
        }
    }

    private fun deliveries() = _state.value.finishedDeliveries

    private fun inProgressDelivery() = _state.value.inProgressDelivery
}