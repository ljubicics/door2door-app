package com.example.door2door_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.door2door_app.home.ui.HomeScreen
import com.example.door2door_app.login.ui.LoginScreen
import com.example.door2door_app.register.ui.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppDestinations.LoginScreenPath()) {
        composable<AppDestinations.LoginScreenPath> {
            LoginScreen(
                onSuccessfulLogin = {
                    navigateAndForget(
                        navController = navController,
                        destination = AppDestinations.HomeScreenPath,
                    )
                },
                onRegisterClick = {
                    navController.navigate(
                        route = AppDestinations.RegisterScreenPath,
                    )
                }
            )
        }
        composable<AppDestinations.RegisterScreenPath> {
            RegisterScreen(
                navController = navController,
                onSuccessfulRegister = {
                    navigateAndForget(
                        navController = navController,
                        destination = AppDestinations.HomeScreenPath,
                    )
                }
            )
        }
        composable<AppDestinations.HomeScreenPath> {
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
