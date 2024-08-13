package com.example.door2door_app.delivery.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.ui.theme.Door2DoorAppTheme

@Composable
fun DeliveryActions(
    modifier: Modifier,
    delivery: Delivery?
) {

    val deliveryStatusText = when (delivery?.status) {
        DeliveryStatus.IN_PROGRESS -> "In Progress"
        DeliveryStatus.ACCEPTED -> "Accepted"
        else -> "Pending"
    }

    Card(
        Modifier
            .padding(paddingValues = PaddingValues(start = 16.dp, end = 16.dp))
            .clip(shape = RoundedCornerShape(30.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = deliveryStatusText,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp
                )
            }
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.padding(paddingValues = PaddingValues(end = 16.dp)),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.secondary,
                    )
                ) {
                    Text(
                        text = "Details >",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Row {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp)),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                )
            ) {
                Text(text = "Accept")
            }
        }
        Row {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = PaddingValues(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp)),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                )
            ) {
                Text(text = "Navigate to")
            }
        }
    }
}

@Preview
@Composable
fun PreviewDeliveryActions() {
    Door2DoorAppTheme {
        Surface {
            DeliveryActions(
                modifier = Modifier.fillMaxWidth(),
                delivery = Delivery(
                    status = DeliveryStatus.ACCEPTED
                )
            )
        }
    }
}