package com.example.door2door_app.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.door2door_app.R
import com.example.door2door_app.navigation.AppDestinations
import com.example.door2door_app.navigation.IDestination
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
    onSplashScreenEnd: (IDestination) -> Unit
) {
    LaunchedEffect(key1 = null) {
        splashViewModel.checkIfUserLoggedIn()
        delay(800)
        splashViewModel.nextScreen.collect {
            when (it) {
                NextScreen.Login -> onSplashScreenEnd.invoke(AppDestinations.LoginScreenPath())
                NextScreen.Main -> onSplashScreenEnd.invoke(AppDestinations.MainScreenPath)
            }
        }
    }
    SplashScreenContent(
        modifier = Modifier
    )
}

@Composable
private fun SplashScreenContent(
    modifier: Modifier
) {
    Surface {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.door2door_logo),
                modifier = Modifier
                    .size(250.dp),
                contentDescription = ""
            )
        }
    }
}