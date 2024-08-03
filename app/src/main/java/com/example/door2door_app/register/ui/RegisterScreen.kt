package com.example.door2door_app.register.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.door2door_app.R
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = koinViewModel(),
    navController: NavController,
    onSuccessfulRegister: () -> Unit
) {

    val state by registerViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        registerViewModel.nextScreen.collect {
            onSuccessfulRegister()
        }
    }

    LaunchedEffect(key1 = null) {
        registerViewModel.registerError.collect {
            Toast.makeText(
                context,
                R.string.register_error,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    RegisterScreenContent(
        modifier = Modifier,
        username = state.username,
        password = state.password,
        name = state.name,
        surname = state.surname,
        email = state.email,
        phoneNumber = state.phoneNumber,
        address = state.address,
        onBackClicked = {
            navController.popBackStack()
        },
        onRegisterClicked = registerViewModel::register,
        onUsernameChange = registerViewModel::setUsername,
        onPasswordChange = registerViewModel::setPassword,
        onNameChange = registerViewModel::setName,
        onSurnameChange = registerViewModel::setSurname,
        onEmailChange = registerViewModel::setEmail,
        onPhoneNumberChange = registerViewModel::setPhoneNumber,
        onAddressChange = registerViewModel::setAddress,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    name: String,
    surname: String,
    email: String,
    phoneNumber: String,
    address: String,
    onBackClicked: () -> Unit = {},
    onRegisterClicked: () -> Unit = {},
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onSurnameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPhoneNumberChange: (String) -> Unit = {},
    onAddressChange: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onBackClicked()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                },
                title = {
                    Text(
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        text = stringResource(id = R.string.register)
                    )
                }
            )
        },
        content = { innerPadding ->
            RegistrationForm(
                modifier = modifier,
                innerPadding = innerPadding,
                username = username,
                password = password,
                name = name,
                surname = surname,
                email = email,
                phoneNumber = phoneNumber,
                address = address,
                onRegisterClicked = onRegisterClicked,
                onUsernameChange = onUsernameChange,
                onPasswordChange = onPasswordChange,
                onNameChange = onNameChange,
                onSurnameChange = onSurnameChange,
                onEmailChange = onEmailChange,
                onPhoneNumberChange = onPhoneNumberChange,
                onAddressChange = onAddressChange
            )
        }
    )
}

@Composable
private fun RegistrationForm(
    modifier: Modifier,
    innerPadding: PaddingValues,
    username: String,
    password: String,
    name: String,
    surname: String,
    email: String,
    phoneNumber: String,
    address: String,
    onRegisterClicked: () -> Unit = {},
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onSurnameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPhoneNumberChange: (String) -> Unit = {},
    onAddressChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            value = username,
            label = {
                Text(text = stringResource(id = R.string.username))
            },
            onValueChange = onUsernameChange
        )
        OutlinedTextField(
            value = password,
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = name,
            label = {
                Text(text = stringResource(id = R.string.name))
            },
            onValueChange = onNameChange
        )
        OutlinedTextField(
            value = surname,
            label = {
                Text(text = stringResource(id = R.string.surname))
            },
            onValueChange = onSurnameChange
        )
        OutlinedTextField(
            value = email,
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            onValueChange = onEmailChange
        )
        OutlinedTextField(
            value = phoneNumber,
            label = {
                Text(text = stringResource(id = R.string.phone))
            },
            onValueChange = onPhoneNumberChange
        )
        OutlinedTextField(
            value = address,
            label = {
                Text(text = stringResource(id = R.string.address))
            },
            onValueChange = onAddressChange
        )
        Spacer(modifier = modifier.height(30.dp))
        Button(
            onClick = { 
                onRegisterClicked()
            }
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    Door2DoorAppTheme {
        Surface {
            RegisterScreenContent(
                username = "",
                password = "",
                name = "",
                surname = "",
                email = "",
                phoneNumber = "",
                address = ""
            )
        }
    }
}