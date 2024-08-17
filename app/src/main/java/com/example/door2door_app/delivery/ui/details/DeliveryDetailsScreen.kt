package com.example.door2door_app.delivery.ui.details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.delivery.ui.components.details.DeliveryDetails
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.Account
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeliveryDetailsScreen(
    deliveryId: Long = 0,
    viewModel: DeliveryDetailsViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadDelivery(deliveryId = deliveryId)
    }

    DeliveryDetailsScreenContent(
        delivery = state.delivery
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeliveryDetailsScreenContent(
    delivery: Delivery?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
    ) { internalPadding ->
        DeliveryDetails(internalPadding, delivery)
    }
}


@Preview
@Composable
fun PreviewDeliveryDetailsScreen() {
    Door2DoorAppTheme {
        Surface {
            DeliveryDetailsScreenContent(
                delivery = Delivery(
                    id = 1,
                    status = DeliveryStatus.IN_PROGRESS,
                    sender = Account(
                        username = "John"
                    ),
                    driver = Account(
                        username = "Nikola"
                    )
                )
            )
        }
    }
}