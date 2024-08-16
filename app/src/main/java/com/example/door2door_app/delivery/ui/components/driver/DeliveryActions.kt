package com.example.door2door_app.delivery.ui.components.driver

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
import androidx.compose.ui.platform.LocalContext
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
    delivery: Delivery?,
    onDeliveryStatusButtonClick: () -> Unit = {},
    onNavigationButtonClick: () -> Unit = {}
) {

    val deliveryStatusNextStateText = when (delivery?.status) {
        DeliveryStatus.IN_PROGRESS -> "Scan QR to confirm"
        DeliveryStatus.ACCEPTED -> "Picked up"
        else -> "Pending"
    }

    val navigationText = when (delivery?.status) {
        DeliveryStatus.IN_PROGRESS -> "Navigate to drop-off"
        DeliveryStatus.ACCEPTED -> "Navigate to pickup"
        else -> "Navigate to"
    }

    val context = LocalContext.current

    Card(
        Modifier
            .padding(paddingValues = PaddingValues(start = 16.dp, end = 16.dp))
            .clip(shape = RoundedCornerShape(30.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.padding(paddingValues = PaddingValues(16.dp)),
            ) {
                Text(
                    text = "Manage current delivery",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 4.dp)),
                    onClick = {
                        onDeliveryStatusButtonClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.secondary,
                    )
                ) {
                    Text(
                        text = deliveryStatusNextStateText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)),
                    onClick = {
                        if (delivery != null) {
                            onNavigationButtonClick()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.secondary,
                    )
                ) {
                    Text(
                        text = navigationText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
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