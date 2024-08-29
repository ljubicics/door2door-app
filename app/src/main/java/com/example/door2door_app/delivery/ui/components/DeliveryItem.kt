package com.example.door2door_app.delivery.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.R
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.delivery.domain.model.DeliveryStatus
import com.example.door2door_app.delivery.ui.components.util.DeliveryDayAndMonth
import com.example.door2door_app.delivery.ui.components.util.TimeDeliveredResolver
import com.example.door2door_app.ui.theme.Door2DoorAppTheme


@Composable
fun DeliveryItem(
    delivery: Delivery? = null,
    onDeliveryItemClick: (Delivery) -> Unit = {}
) {

    var dayAndMonthDelivered by remember {
        mutableStateOf(DeliveryDayAndMonth())
    }

    val deliveryStatusText = when (delivery?.status) {
        DeliveryStatus.IN_PROGRESS -> stringResource(R.string.in_progress)
        DeliveryStatus.ACCEPTED -> stringResource(R.string.picking_up)
        DeliveryStatus.DELIVERED -> stringResource(R.string.delivered)
        else -> stringResource(R.string.pending)
    }

    dayAndMonthDelivered =
        TimeDeliveredResolver.resolveTimeDelivered(delivery?.timeDelivered ?: System.currentTimeMillis())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(paddingValues = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = PaddingValues(all = 10.dp))
                    .clickable {
                        delivery?.let {
                            onDeliveryItemClick(it)
                        }
                    },
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.padding(8.dp),
                            painter = painterResource(
                                id = R.drawable.delivery_package_color
                            ),
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = delivery?.trackingCode ?: stringResource(R.string.no_tracking_code_available),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = deliveryStatusText,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                delivery?.let {
                    if (it.status == DeliveryStatus.DELIVERED) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = dayAndMonthDelivered.day.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(lineHeight = 24.sp),
                                color = Color.Black
                            )
                            Text(
                                text = dayAndMonthDelivered.month.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                style = TextStyle(lineHeight = 12.sp),
                                color = Color.DarkGray
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = ">",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DeliveryItemPreview() {
    Door2DoorAppTheme {
        Surface {
            DeliveryItem(
                Delivery(
                    trackingCode = "UG123123UG",
                    timeDelivered = System.currentTimeMillis(),
                    status = DeliveryStatus.DELIVERED
                )
            )
        }
    }
}