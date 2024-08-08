package com.example.door2door_app.navigation.bottom

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.door2door_app.navigation.IDestination
import com.example.door2door_app.navigation.CustomerDestinations

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            path = CustomerDestinations.DeliveryScreenPath,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Default.Home
        ),
        BottomNavigationItem(
            title = "Payment",
            path = CustomerDestinations.DeliveryScreenPath,
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Default.Add,
        )
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        modifier = Modifier.height(80.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.path)
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val path: IDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)