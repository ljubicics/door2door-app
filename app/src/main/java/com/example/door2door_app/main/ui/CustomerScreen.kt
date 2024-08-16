package com.example.door2door_app.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.door2door_app.navigation.CustomerNavGraph
import com.example.door2door_app.navigation.bottom.BottomNavigationBar

@Composable
fun CustomerScreen() {
    val bottomBarNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomBarNavController)
        },
        content = { innerPadding ->
            CustomerNavGraph(
                modifier = Modifier.padding(paddingValues = innerPadding),
                navController = bottomBarNavController
            )
        }
    )
}