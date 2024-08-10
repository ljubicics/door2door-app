package com.example.door2door_app.delivery.ui.driver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.ui.driver.components.DeliveriesView
import com.example.door2door_app.delivery.ui.driver.components.DeliveryInProgressItem
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DriverDeliveriesScreen(
    driverDeliveriesViewModel: DriverDeliveriesViewModel = koinViewModel(),
) {
    val state by driverDeliveriesViewModel.state.collectAsStateWithLifecycle()

    DriverDeliveriesScreenContent(
        deliveries = state.deliveries
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DriverDeliveriesScreenContent(
    deliveries: List<Delivery> = emptyList()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Your Deliveries",
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                }
            )
        }
    ) { innerPadding ->
//        val list = mutableListOf<Delivery>().apply {
//            repeat(10) {
//                add(
//                    Delivery(
//                        trackingCode = "UG123123UG"
//                    )
//                )
//            }
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding())),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DeliveryInProgressItem(delivery = deliveries.firstOrNull())
            DeliveriesView(deliveries = deliveries)
        }
    }
}

@Preview
@Composable
fun PreviewDriverDeliverisScreen() {
    Door2DoorAppTheme {
        DriverDeliveriesScreen()
    }
}