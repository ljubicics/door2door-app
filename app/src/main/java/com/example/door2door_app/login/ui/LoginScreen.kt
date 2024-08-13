package com.example.door2door_app.login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.R
import com.example.door2door_app.navigation.AppDestinations
import com.example.door2door_app.navigation.IDestination
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    onSuccessfulLogin: (IDestination) -> Unit,
    onRegisterClick: () -> Unit
) {

    val state by loginViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        loginViewModel.nextScreen.collect {
            when (it) {
                NextScreen.Customer -> onSuccessfulLogin(AppDestinations.Customer)
                NextScreen.DeliveryDriver -> onSuccessfulLogin(AppDestinations.DeliveryDriver)
            }
        }
    }

    LaunchedEffect(key1 = null) {
        loginViewModel.loginError.collect {
            Toast.makeText(
                context,
                R.string.login_failed,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = Modifier.imePadding()) {
        LoginScreenContent(
            modifier = Modifier,
            username = state.username,
            password = state.password,
            onUsernameChange = loginViewModel::setUsername,
            onPasswordChange = loginViewModel::setPassword,
            onLoginClicked = loginViewModel::onLoginClick,
            onRegisterClick = onRegisterClick
        )
    }
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(id = R.drawable.door2door_logo),
            contentDescription = null,
        )
        LoginForm(
            modifier = Modifier,
            username = username,
            password = password,
            loginButtonText = "Login",
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange,
            onLoginClicked = onLoginClicked,
            onRegisterClick = onRegisterClick
        )
        Spacer(modifier = modifier.height(100.dp))
    }
}

@Composable
fun LoginForm(
    modifier: Modifier,
    username: String,
    password: String,
    loginButtonText: String,
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .width(300.dp)
                .height(55.dp),
            value = username,
            placeholder = { Text(text = "Username") },
            maxLines = 1,
            onValueChange = onUsernameChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
            }
        )

        Spacer(modifier = modifier.height(20.dp))

        TextField(
            modifier = Modifier
                .width(300.dp)
                .height(55.dp),
            value = password,
            placeholder = { Text(text = "Password") },
            onValueChange = onPasswordChange,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
            }
        )

        Spacer(modifier = modifier.height(10.dp))

        Row {
            Text(
                text = "Don't have an account? ",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = modifier.clickable {
                    onRegisterClick()
                },
                text = "Register here",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            modifier = modifier.width(200.dp),
            content = { Text(text = loginButtonText) },
            onClick = onLoginClicked,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    Door2DoorAppTheme {
        Surface {
            LoginScreenContent(
                username = "",
                password = "",
                onUsernameChange = {},
                onPasswordChange = {}
            )
        }
    }
}