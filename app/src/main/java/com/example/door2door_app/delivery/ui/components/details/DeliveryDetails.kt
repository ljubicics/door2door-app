package com.example.door2door_app.delivery.ui.components.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.R
import com.example.door2door_app.delivery.domain.model.Delivery

@Composable
fun DeliveryDetails(
    internalPadding: PaddingValues,
    delivery: Delivery?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(
                paddingValues = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = internalPadding.calculateTopPadding() + 16.dp
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
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .size(70.dp)
                                .padding(12.dp),
                            painter = painterResource(
                                id = R.drawable.delivery_package_color
                            ),
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 16.dp),
                ) {
                    Row {
                        Text(
                            text = delivery?.trackingCode ?: stringResource(R.string.no_tracking_code),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            text = stringResource(R.string.delivery_in_progress),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                Modifier
                    .padding(paddingValues = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp))
                    .clip(shape = RoundedCornerShape(30.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues = PaddingValues(16.dp))
                ) {
                    Row {
                        Column {
                            Text(
                                text = stringResource(R.string.from),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = delivery?.sender?.username ?: "",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Column {
                            Text(
                                text = stringResource(R.string.delivery_driver),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            delivery?.driver?.username?.let {
                                Text(
                                    text = it,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}