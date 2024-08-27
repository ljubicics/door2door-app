package com.example.door2door_app.user.data.repository

import com.example.door2door_app.user.data.model.UserDto
import com.example.door2door_app.user.domain.model.User

object UserMapper {
    fun map(userDto: UserDto): User {
        return User(
            name = userDto.name ?: "",
            surname = userDto.surname ?: "",
            email = userDto.email,
            mobileNumber = userDto.mobileNumber,
            address = userDto.address,
            timeCreated = userDto.timeCreated,
        )
    }
}