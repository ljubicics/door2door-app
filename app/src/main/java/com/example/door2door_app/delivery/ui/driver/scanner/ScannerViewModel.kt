package com.example.door2door_app.delivery.ui.driver.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.usecase.driver.ConfirmDeliveryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val confirmDeliveryUseCase: ConfirmDeliveryUseCase
) : ViewModel() {

    private val _nextScreen = Channel<Unit>()
    val nextScreen = _nextScreen.receiveAsFlow()

    fun confirmDelivery(confirmPath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = confirmDeliveryUseCase(confirmPath = confirmPath)
            _nextScreen.send(Unit)
        }
    }

}