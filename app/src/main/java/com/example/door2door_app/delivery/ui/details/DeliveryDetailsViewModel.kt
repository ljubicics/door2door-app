package com.example.door2door_app.delivery.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.usecase.details.GetDeliveryDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeliveryDetailsViewModel(
    private val getDeliveryDetailsUseCase: GetDeliveryDetailsUseCase
) : ViewModel() {

    data class State(
        val delivery: Delivery? = null,
        val isLoading: Boolean = false
    )

    private val _state = MutableStateFlow(State())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), State())

    fun loadDelivery(deliveryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            setIsLoading(isLoading = true)
            val delivery = getDeliveryDetailsUseCase(deliveryId)
            if (delivery != null) {
                _state.update { it.copy(delivery = delivery) }
            }
            setIsLoading(isLoading = false)
        }
    }

    private fun setIsLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}