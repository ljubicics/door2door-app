package com.example.door2door_app.delivery.ui.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.usecase.GetAllFinishedDriverDeliveriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DriverDeliveriesViewModel(
    private val getAllFinishedDriverDeliveriesUseCase: GetAllFinishedDriverDeliveriesUseCase
) : ViewModel() {

    data class State(
        val deliveries: List<Delivery> = emptyList()
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    init {
        loadDeliveries()
    }

    private fun loadDeliveries() {
        viewModelScope.launch(Dispatchers.IO) {
            val deliveries = getAllFinishedDriverDeliveriesUseCase()
            _state.update { it.copy(deliveries = deliveries) }
        }
    }

    private fun deliveries() = _state.value.deliveries
}