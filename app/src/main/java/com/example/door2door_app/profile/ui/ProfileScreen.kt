package com.example.door2door_app.profile.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.MainActivity
import com.example.door2door_app.R
import com.example.door2door_app.profile.ui.components.ProfileDetails
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.Account
import com.example.door2door_app.user.domain.model.User
import com.example.door2door_app.websockets.WebSocketClient
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    webSocketClient: WebSocketClient? = null,
    onLogOutClick: () -> Unit = {},
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = null) {
        viewModel.loadUser()
    }

    LaunchedEffect(key1 = null) {
        viewModel.logout.collect {
            onLogOutClick()
        }
    }

    ProfileScreenContent(
        user = state.user,
        account = state.account,
        isLoading = state.isLoading,
        webSocketClient = webSocketClient,
        onLogOutClick = viewModel::onLogOutClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    user: User? = null,
    account: Account? = null,
    isLoading: Boolean = false,
    webSocketClient: WebSocketClient? = null,
    onLogOutClick: () -> Unit = {}
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val context = LocalContext.current

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    } else {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.your_profile),
                            fontWeight = FontWeight.W300,
                            fontSize = 26.sp
                        )
                    },
                    colors = TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .padding(paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 32.dp))
                        .background(
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            (context as? MainActivity)?.let { webSocketClient?.disconnect(it) }
                            onLogOutClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                paddingValues = PaddingValues(
                                    start = 30.dp,
                                    end = 10.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = CircleShape
                                )
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(30.dp),
                                painter = painterResource(
                                    id = R.drawable.log_out
                                ),
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.size(32.dp))
                        Text(
                            text = stringResource(R.string.log_out),
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            },
            sheetPeekHeight = 250.dp,
            sheetDragHandle = {},
            sheetShadowElevation = 10.dp,
            sheetSwipeEnabled = false
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(
                        paddingValues = PaddingValues(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    )
            ) {
                ProfileDetails(
                    modifier = Modifier.padding(16.dp),
                    user = user,
                    account = account
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    Door2DoorAppTheme {
        Surface {
            ProfileScreen()
        }
    }
}