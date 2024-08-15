package com.example.door2door_app.delivery.ui.driver

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.ui.components.driver.DeliveriesView
import com.example.door2door_app.delivery.ui.components.driver.DeliveryInProgressItem
import com.example.door2door_app.delivery.ui.components.driver.NoActiveDeliveryItem
import com.example.door2door_app.delivery.ui.components.driver.NoDeliveriesView
import com.example.door2door_app.delivery.ui.components.util.NavigationDestinationResolver
import com.example.door2door_app.navigation.DeliveryDriverDestinations
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.User
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DriverDeliveriesScreen(
    navController: NavController,
    viewmodel: DriverDeliveriesViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val cameraPermissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    viewmodel.loadScreenInfo()

    LaunchedEffect(key1 = null) {
        viewmodel.onNavigationButtonClick.collect {
            val destination = state.inProgressDelivery?.let { delivery ->
                NavigationDestinationResolver.resolveDestination(
                    delivery = delivery
                )
            }
            val gmmIntentUri = Uri.parse("google.navigation:q=$destination,+Belgrade+Serbia")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(context, mapIntent, null)
        }
    }

    LaunchedEffect(key1 = null) {
        viewmodel.openScanner.collect {
            if (cameraPermissionState.status.isGranted) {
                navController.navigate(DeliveryDriverDestinations.ScannerScreenPath)
            } else {
                cameraPermissionState.run { launchPermissionRequest() }
            }
        }
    }

    DriverDeliveriesScreenContent(
        user = state.user ?: User(),
        inProgressDelivery = state.inProgressDelivery,
        deliveries = state.finishedDeliveries,
        onNavigationButtonClick = viewmodel::onNavigationButtonClick,
        onDeliveryStatusButtonClick = viewmodel::onDeliveryStatusButtonClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DriverDeliveriesScreenContent(
    user: User = User(),
    inProgressDelivery: Delivery? = null,
    deliveries: List<Delivery> = emptyList(),
    onDeliveryStatusButtonClick: () -> Unit = {},
    onNavigationButtonClick: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (deliveries.size > 0) {
                DeliveriesView(deliveries = deliveries)
            } else {
                NoDeliveriesView()
            }
        },
        sheetPeekHeight = 420.dp,
        sheetDragHandle = {},
        sheetShadowElevation = 10.dp,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = " - Hello, ${user.name}",
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding()))
        ) {
            if (inProgressDelivery == null) {
                NoActiveDeliveryItem()
            } else {
                DeliveryInProgressItem(
                    delivery = inProgressDelivery,
                    onDeliveryStatusButtonClick = onDeliveryStatusButtonClick,
                    onNavigationButtonClick = onNavigationButtonClick
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDriverDeliverisScreen() {
    val list = mutableListOf<Delivery>().apply {
        repeat(10) {
            add(
                Delivery(
                    trackingCode = "UG123123UG"
                )
            )
        }
    }
    Door2DoorAppTheme {
        DriverDeliveriesScreenContent(User(name = "Strahinja"), Delivery(), list)
    }
}