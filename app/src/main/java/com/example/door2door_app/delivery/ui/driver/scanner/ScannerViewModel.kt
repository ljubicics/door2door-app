package com.example.door2door_app.delivery.ui.driver.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.delivery.domain.usecase.driver.ConfirmDeliveryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val confirmDeliveryUseCase: ConfirmDeliveryUseCase
) : ViewModel() {

    fun confirmDelivery(confirmPath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            confirmDeliveryUseCase(confirmPath = confirmPath)
        }
    }
}