package com.example.door2door_app.delivery.ui.components.util

import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus

object NavigationDestinationResolver {
    fun resolveDestination(delivery: Delivery): String {
        return when (delivery.status) {
            DeliveryStatus.ACCEPTED -> extract(delivery.pickupLocation)
            DeliveryStatus.IN_PROGRESS -> extract(delivery.deliveryLocation)
            else -> ""
        }
    }

    private fun extract(location: String): String {
        val items = location.split(" ")
        var extractedLocation = ""
        items.forEach { item ->
            extractedLocation += "$item+"
        }
        return extractedLocation
    }
}