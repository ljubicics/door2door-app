package com.example.door2door_app.delivery.data.mapper

import com.example.door2door_app.delivery.data.dto.DeliveryDto
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.RoleName

object DeliveryMapper {
    fun map(dto: DeliveryDto): Delivery {
        return Delivery(
            timeStarted = dto.timeStarted,
            timeDelivered = dto.timeDelivered,
            trackingCode = dto.trackingCode,
            status = dto.status,
            qrConfirmed = dto.qrConfirmed,
            pickupLocation = dto.pickupLocation,
            deliveryLocation = dto.deliveryLocation,
            sender = Account(
                id = dto.sender?.id ?: 0,
                username = dto.sender?.username ?: "",
                role = dto.sender?.role?.name ?: RoleName.UNKNOWN
            ),
            receiver = Account(
                id = dto.receiver?.id ?: 0,
                username = dto.receiver?.username ?: "",
                role = dto.receiver?.role?.name ?: RoleName.UNKNOWN
            ),
            driver = Account(
                id = dto.driver?.id ?: 0,
                username = dto.driver?.username ?: "",
                role = dto.driver?.role?.name ?: RoleName.UNKNOWN
            )
        )
    }

}