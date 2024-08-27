package com.example.door2door_app.delivery.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.door2door_app.R
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.delivery.ui.components.details.DeliveryDetails
import com.example.door2door_app.delivery.ui.details.qrgenerator.QrCodeGenerator
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.Account
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeliveryDetailsScreen(
    deliveryId: Long = 0,
    onBackPressed: () -> Unit = {},
    viewModel: DeliveryDetailsViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadDelivery(deliveryId = deliveryId)
    }

    DeliveryDetailsScreenContent(
        delivery = state.delivery,
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeliveryDetailsScreenContent(
    delivery: Delivery?,
    onBackPressed: () -> Unit = { }
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.delivery_details),
                        fontWeight = FontWeight.W300,
                        fontSize = 26.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            onBackPressed()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(
                        paddingValues = PaddingValues(
                            start = 32.dp,
                            end = 32.dp,
                            top = 16.dp
                        )
                    )
                    .clip(shape = RoundedCornerShape(40.dp)),
                colors = CardColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = MaterialTheme.colorScheme.tertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(
                            paddingValues = PaddingValues(
                                start = 32.dp,
                                end = 32.dp,
                                bottom = 16.dp
                            )
                        ),
                        text = stringResource(R.string.show_qr_code_beneath_to_your_delivery_driver),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    val bitmap = QrCodeGenerator.encodeAsBitmap(
                        str = "id=${delivery?.id}&code=${delivery?.trackingCode}&receiverId=${delivery?.receiver?.id}",
                        width = 700,
                        height = 700
                    )
                    bitmap?.asImageBitmap()?.let {
                        Image(
                            modifier = Modifier.clip(shape = RoundedCornerShape(size = 30.dp)),
                            bitmap = it,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        sheetPeekHeight = 470.dp,
        sheetDragHandle = {},
        sheetShadowElevation = 10.dp,
        sheetSwipeEnabled = false
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding()))
        ) {
            DeliveryDetails(delivery = delivery)
        }
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
                    receiver = Account(
                        id = 2,
                        username = "Jane"
                    ),
                    sender = Account(
                        username = "John"
                    ),
                    driver = Account(
                        username = "Nikola"
                    ),
                    trackingCode = "FS804026922MS"
                )
            )
        }
    }
}