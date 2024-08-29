package com.example.door2door_app.delivery.ui.customer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.door2door_app.R
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.ui.components.customer.CustomerDeliveriesView
import com.example.door2door_app.delivery.ui.components.details.DeliveryDetails
import com.example.door2door_app.navigation.CustomerDestinations
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.User
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomerDeliveriesScreen(
    viewModel: CustomerDeliveriesViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = null) {
        viewModel.onActiveDeliveryClick.collectLatest { delivery ->
            navController.navigate(CustomerDestinations.DeliveryDetailsPath(deliveryId = delivery.id))
        }
    }

    LaunchedEffect(key1 = null) {
        viewModel.onFinishedDeliveryClick.collectLatest { delivery ->
            viewModel.selectDelivery(delivery = delivery)
            viewModel.showDeliveryDetailsPopup()
        }
    }

    CustomerDeliveriesScreenContent(
        user = state.user,
        activeDeliveries = state.activeDeliveries,
        finishedDeliveries = state.finishedDeliveries,
        isLoading = state.isLoading,
        showDeliveryDetailsPopup = state.showDeliveryDetailsPopup,
        selectedDelivery = state.selectedDelivery,
        onActiveDeliveryClick = viewModel::onActiveDeliveryClick,
        onFinishedDeliveryClick = viewModel::onFinishedDeliveryClick,
        onDismissPopup = viewModel::onDismissPopup
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDeliveriesScreenContent(
    modifier: Modifier = Modifier,
    user: User? = null,
    activeDeliveries: List<Delivery> = emptyList(),
    finishedDeliveries: List<Delivery> = emptyList(),
    isLoading: Boolean = false,
    showDeliveryDetailsPopup: Boolean = false,
    selectedDelivery: Delivery? = null,
    onActiveDeliveryClick: (Delivery) -> Unit = {},
    onFinishedDeliveryClick: (Delivery) -> Unit = {},
    onDismissPopup: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (showDeliveryDetailsPopup) {
                    Modifier.blur(4.dp)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            if (activeDeliveries.isEmpty() && finishedDeliveries.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_deliveries),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                if (showDeliveryDetailsPopup) {
                    Popup(
                        alignment = Alignment.Center,
                        properties = PopupProperties(
                            dismissOnClickOutside = true,
                            dismissOnBackPress = true
                        )
                    ) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            DeliveryDetails(delivery = selectedDelivery)
                        }
                    }
                    BackHandler {
                        onDismissPopup()
                    }
                }
                Scaffold(
                    topBar = {

                        TopAppBar(
                            title = {
                                Text(
                                    text = " - Hello, ${user?.name}",
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
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(innerPadding)
                            .background(color = MaterialTheme.colorScheme.surface)
                            .then(modifier),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            modifier = Modifier.padding(paddingValues = PaddingValues(top = 16.dp, bottom = 8.dp)),
                        ) {
                            Text(
                                text = stringResource(R.string.all_deliveries),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        CustomerDeliveriesView(
                            modifier = modifier.padding(bottom = 60.dp),
                            activeDeliveries = activeDeliveries,
                            finishedDeliveries = finishedDeliveries,
                            onActiveDeliveryClick = onActiveDeliveryClick,
                            onFinishedDeliveryClick = onFinishedDeliveryClick
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CustomerDeliveriesScreenPreview() {
    val activeDeliveries = mutableListOf<Delivery>().apply {
        repeat(3) {
            add(
                Delivery(
                    trackingCode = "UG321321UG"
                )
            )
        }
    }

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
        Surface {
            CustomerDeliveriesScreenContent(
                activeDeliveries = activeDeliveries,
                finishedDeliveries = list,
                user = User("Strahinja"),
                isLoading = false
            )
        }
    }
}