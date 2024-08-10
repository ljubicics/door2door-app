package com.example.door2door_app.delivery.ui.driver.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.Account


@Composable
fun DeliveriesView(
    modifier: Modifier = Modifier,
    deliveries: List<Delivery>,
    account: Account? = null
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(start = 16.dp, top = 16.dp, bottom = 16.dp)),
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Finished deliveries",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = modifier.height(8.dp))

        LazyColumn {
            itemsIndexed(items = deliveries, key = { index, _ -> index }) { index, delivery ->
                DeliveryItem(
                    trackingCode = delivery.trackingCode ?: "UG123123UG"
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(PaddingValues(start = 20.dp, end = 20.dp))
                        .background(color = Color.Gray)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewDeliveriesView() {
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
            DeliveriesView(deliveries = list)
        }
    }
}
