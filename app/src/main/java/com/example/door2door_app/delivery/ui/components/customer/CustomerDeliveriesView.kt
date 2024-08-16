package com.example.door2door_app.delivery.ui.components.customer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.door2door_app.delivery.ui.components.DeliveryItem
import com.example.door2door_app.ui.theme.Door2DoorAppTheme

@Composable
fun CustomerDeliveriesView(
    modifier: Modifier = Modifier,
    activeDeliveries: List<Delivery>,
    finishedDeliveries: List<Delivery>
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items = activeDeliveries, key = { index, _ -> index }) { index, delivery ->
            DeliveryItem(
                trackingCode = delivery.trackingCode ?: "UG123123UG"
            )
        }
        item {
            if (finishedDeliveries.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .width(60.dp)
                            .background(color = MaterialTheme.colorScheme.outline)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues = PaddingValues(top = 10.dp, bottom = 16.dp))
                            .background(color = MaterialTheme.colorScheme.surface),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.finished_deliveries),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        itemsIndexed(
            items = finishedDeliveries,
            key = { index, _ -> index + finishedDeliveries.size }) { index, delivery ->
            DeliveryItem(
                trackingCode = delivery.trackingCode ?: "UG123123UG"
            )
        }
    }
}

@Preview
@Composable
fun PreviewCustomerDeliveriesView() {
    val activeDeliveries = mutableListOf<Delivery>().apply {
        repeat(3) {
            add(
                Delivery(
                    trackingCode = "UG321321UG"
                )
            )
        }
    }
    val finishedDeliveries = mutableListOf<Delivery>().apply {
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
            CustomerDeliveriesView(
                activeDeliveries = activeDeliveries,
                finishedDeliveries = finishedDeliveries
            )
        }
    }
}
