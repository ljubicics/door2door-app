package com.example.door2door_app.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.R
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    onSuccessfulLogin: () -> Unit
) {

    val state by loginViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = null) {
        loginViewModel.nextScreen.collect {
            onSuccessfulLogin()
        }
    }

    LoginScreenContent(
        modifier = Modifier,
        username = state.username,
        password = state.password,
        onUsernameChange = loginViewModel::setUsername,
        onPasswordChange = loginViewModel::setPassword,
        onLoginClicked = loginViewModel::onLoginClick
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClicked: () -> Unit = {}
) {
    Surface {
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
                onLoginClicked = onLoginClicked
            )
        }

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
    onLoginClicked: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.width(350.dp),
            value = username,
            placeholder = { Text(text = "Email") },
            maxLines = 1,
            onValueChange = onUsernameChange,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = modifier.height(10.dp))

        TextField(
            modifier = Modifier.width(350.dp),
            value = password,
            placeholder = { Text(text = "Password") },
            onValueChange = onPasswordChange,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

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
        LoginScreenContent(
            username = "username",
            password = "password",
            onUsernameChange = {},
            onPasswordChange = {}
        )
    }
}