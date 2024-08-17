package com.example.door2door_app.delivery.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.model.Delivery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DeliveryDetailsViewModel(

) : ViewModel() {

    data class State(
        val delivery: Delivery? = null
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    fun loadDelivery(deliveryId: Long) {
        // Load delivery by id
    }
}