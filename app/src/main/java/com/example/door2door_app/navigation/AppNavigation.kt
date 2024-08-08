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
import com.example.door2door_app.main.ui.CustomerScreen
import com.example.door2door_app.main.ui.DeliveryDriverScreen
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
                        destination = it,
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
                        destination = AppDestinations.Customer,
                    )
                }
            )
        }
        composable<AppDestinations.Customer> {
            CustomerScreen()
        }
        composable<AppDestinations.DeliveryDriver> {
            DeliveryDriverScreen()
        }
    }
}

@Composable
fun CustomerNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = CustomerDestinations.DeliveryScreenPath) {
        composable<CustomerDestinations.DeliveryScreenPath> {
            DeliveryScreen(
                modifier = modifier,
                text = "Customer"
            )
        }
    }
}

@Composable
fun DeliveryDriverNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = DeliveryDriverDestinations.DeliveryScreenPath) {
        composable<DeliveryDriverDestinations.DeliveryScreenPath> {
            DeliveryScreen(
                modifier = modifier,
                text = "Delivery Driver"
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
