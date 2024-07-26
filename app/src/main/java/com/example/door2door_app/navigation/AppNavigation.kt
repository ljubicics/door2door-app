package com.example.door2door_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.door2door_app.home.ui.HomeScreen
import com.example.door2door_app.login.ui.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreenPath) {
        composable<LoginScreenPath> {
            LoginScreen(
                onSuccessfulLogin = {
                    navigateAndForget(
                        navController = navController,
                        destination = HomeScreenPath,
                    )
                }
            )
        }
        composable<HomeScreenPath> {
            HomeScreen()
        }
    }
}

fun navigateAndForget(navController: NavController, destination: IDestination) {
    navController.navigate(destination) {
        popUpTo(0) {
            inclusive = true
        }
    }
}
