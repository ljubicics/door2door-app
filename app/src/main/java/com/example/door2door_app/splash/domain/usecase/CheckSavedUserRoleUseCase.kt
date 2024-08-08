package com.example.door2door_app.splash.domain.usecase

import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.Role
import com.example.door2door_app.user.domain.model.RoleName
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences

class CheckSavedUserRoleUseCase(
    private val preferences: IUserPreferences
) {

    suspend operator fun invoke(): Account {
        val result = preferences.getAccountData()
        if (result.role == RoleName.ROLE_NORMAL_USER) {
            return Account(
                id = result.id,
                username = result.username,
                role = RoleName.ROLE_NORMAL_USER
            )
        } else if (result.role == RoleName.ROLE_DELIVERY) {
            return Account(
                id = result.id,
                username = result.username,
                role = RoleName.ROLE_DELIVERY
            )
        } else {
            return Account(
                role = RoleName.UNKNOWN
            )
        }
    }
}