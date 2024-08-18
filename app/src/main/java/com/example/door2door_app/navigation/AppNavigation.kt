package com.example.door2door_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.door2door_app.delivery.ui.customer.CustomerDeliveriesScreen
import com.example.door2door_app.delivery.ui.details.DeliveryDetailsScreen
import com.example.door2door_app.delivery.ui.driver.DriverDeliveriesScreen
import com.example.door2door_app.delivery.ui.driver.scanner.ScannerScreen
import com.example.door2door_app.login.ui.LoginScreen
import com.example.door2door_app.main.ui.CustomerScreen
import com.example.door2door_app.main.ui.DeliveryDriverScreen
import com.example.door2door_app.profile.ui.ProfileScreen
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
                    navController.navigate(it)
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
            CustomerScreen(
                parentNavController = navController
            )
        }
        composable<AppDestinations.DeliveryDriver> {
            DeliveryDriverScreen(
                parentNavController = navController
            )
        }
    }
}

@Composable
fun CustomerNavGraph(
    modifier: Modifier = Modifier,
    parentNavController: NavController,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = CustomerDestinations.DeliveryScreenPath) {
        composable<CustomerDestinations.DeliveryScreenPath> {
            CustomerDeliveriesScreen(
                navController = navController
            )
        }
        composable<CustomerDestinations.DeliveryDetailsPath> {
            val args = it.toRoute<CustomerDestinations.DeliveryDetailsPath>()
            DeliveryDetailsScreen(
                deliveryId = args.deliveryId
            )
        }
        composable<CustomerDestinations.ProfileScreenPath> {
            ProfileScreen(
                onLogOutClick = {
                    navigateAndForget(
                        navController = parentNavController,
                        destination = AppDestinations.LoginScreenPath()
                    )
                }
            )
        }
    }
}

@Composable
fun DeliveryDriverNavGraph(
    modifier: Modifier = Modifier,
    parentNavController: NavController,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = DeliveryDriverDestinations.DeliveryScreenPath) {
        composable<DeliveryDriverDestinations.DeliveryScreenPath> {
            DriverDeliveriesScreen(
                navController = navController
            )
        }
        composable<DeliveryDriverDestinations.ScannerScreenPath> {
            ScannerScreen(
                navController = navController
            )
        }
        composable<DeliveryDriverDestinations.ProfileScreenPath> {
            ProfileScreen(
                onLogOutClick = {
                    navigateAndForget(
                        navController = parentNavController,
                        destination = AppDestinations.LoginScreenPath()
                    )
                }
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
