package com.example.door2door_app.delivery.ui.driver

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.ui.components.DeliveriesView
import com.example.door2door_app.delivery.ui.components.DeliveryInProgressItem
import com.example.door2door_app.delivery.ui.components.NoDeliveriesView
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.Account
import org.koin.androidx.compose.koinViewModel

@Composable
fun DriverDeliveriesScreen(
    driverDeliveriesViewModel: DriverDeliveriesViewModel = koinViewModel(),
) {
    val state by driverDeliveriesViewModel.state.collectAsStateWithLifecycle()

    driverDeliveriesViewModel.loadDriverInfo()
    driverDeliveriesViewModel.loadDeliveries()

    DriverDeliveriesScreenContent(
        account = state.account ?: Account(),
        inProgressDelivery = state.inProgressDelivery ?: Delivery(),
        deliveries = state.finishedDeliveries
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DriverDeliveriesScreenContent(
    account: Account = Account(),
    inProgressDelivery: Delivery = Delivery(),
    deliveries: List<Delivery> = emptyList()
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
                        text = " - Hello, ${account.username}",
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
            DeliveryInProgressItem(delivery = inProgressDelivery)
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
        DriverDeliveriesScreenContent(Account(), Delivery(), list)
    }
}