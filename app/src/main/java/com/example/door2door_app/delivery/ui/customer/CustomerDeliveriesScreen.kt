package com.example.door2door_app.delivery.ui.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.R
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.ui.components.customer.CustomerDeliveriesView
import com.example.door2door_app.delivery.ui.components.driver.NoDeliveriesView
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.User

@Composable
fun CustomerDeliveriesScreen() {
    val list = mutableListOf<Delivery>().apply {
        repeat(10) {
            add(
                Delivery(
                    trackingCode = "UG123123UG"
                )
            )
        }
    }

    CustomerDeliveriesScreenContent(user = User("Strahinja"), activeDeliveries = list, finishedDeliveries = list)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDeliveriesScreenContent(
    modifier: Modifier = Modifier,
    user: User,
    activeDeliveries: List<Delivery>,
    finishedDeliveries: List<Delivery>
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    Scaffold(
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
                modifier = Modifier.padding(paddingValues = PaddingValues(top = 16.dp)),
            ) {
                Text(
                    text = stringResource(R.string.all_deliveries),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            CustomerDeliveriesView(
                modifier = modifier.padding(bottom = 80.dp),
                activeDeliveries = activeDeliveries,
                finishedDeliveries = finishedDeliveries
            )
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
                user = User("Strahinja")
            )
        }
    }
}