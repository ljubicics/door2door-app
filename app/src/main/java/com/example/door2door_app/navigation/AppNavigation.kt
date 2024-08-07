package com.example.door2door_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.door2door_app.delivery.ui.DeliveryScreen
import com.example.door2door_app.login.ui.LoginScreen
import com.example.door2door_app.main.ui.MainScreen
import com.example.door2door_app.register.ui.RegisterScreen
import com.example.door2door_app.splash.ui.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppDestinations.SplashScreenPath) {
        composable<AppDestinations.SplashScreenPath> {
            SplashScreen(
                onSplashScreenEnd = {
                    navigateAndForget(
                        navController = navController,
                        destination = it,
                    )
                }
            )
        }
        composable<AppDestinations.LoginScreenPath> {
            LoginScreen(
                onSuccessfulLogin = {
                    navigateAndForget(
                        navController = navController,
                        destination = AppDestinations.MainScreenPath,
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
                        destination = AppDestinations.MainScreenPath,
                    )
                }
            )
        }
        composable<AppDestinations.MainScreenPath> {
            MainScreen()
        }
    }
}

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = MainDestinations.DeliveryScreenPath) {
        composable<MainDestinations.DeliveryScreenPath> {
            DeliveryScreen(
                modifier = modifier
            )
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
