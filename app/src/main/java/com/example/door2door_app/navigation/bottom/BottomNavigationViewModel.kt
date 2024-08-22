package com.example.door2door_app.navigation.bottom

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.door2door_app.R
import com.example.door2door_app.navigation.CustomerDestinations
import com.example.door2door_app.navigation.DeliveryDriverDestinations
import com.example.door2door_app.user.domain.model.RoleName
import com.example.door2door_app.user.domain.repository.preferences.IUserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BottomNavigationViewModel(
    val preferences: IUserPreferences,
    context: Context
) : ViewModel() {

    private val _roleItems = MutableStateFlow<List<BottomNavigationItem>>(emptyList())
    val roleItems = _roleItems.asStateFlow()

    init {
        getCurrentUserRoleItems()
    }

    private val deliveryDriverItems = listOf(
        BottomNavigationItem(
            title = "Home",
            path = DeliveryDriverDestinations.DeliveryScreenPath,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = context.getString(R.string.profile),
            path = DeliveryDriverDestinations.ProfileScreenPath,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        )
    )

    private val customerItems = listOf(
        BottomNavigationItem(
            title = "Home",
            path = CustomerDestinations.DeliveryScreenPath,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = context.getString(R.string.profile),
            path = CustomerDestinations.ProfileScreenPath,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        )
    )

    private fun getCurrentUserRoleItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = when (preferences.getAccountData().role) {
                RoleName.ROLE_CUSTOMER -> customerItems
                RoleName.ROLE_DELIVERY -> deliveryDriverItems
                else -> emptyList()
            }
            _roleItems.value = items
        }
    }
}