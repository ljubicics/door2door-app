package com.example.door2door_app.delivery.ui.driver.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.R
import com.example.door2door_app.ui.theme.Door2DoorAppTheme

@Composable
fun DeliveryItem(
    trackingCode: String = ""
) {
    DeliveryItemContent(
        trackingCode = trackingCode
    )
}

@Composable
private fun DeliveryItemContent(
    trackingCode: String = ""
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(paddingValues = PaddingValues(all = 20.dp)),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(
                        id = R.drawable.delivery_package
                    ),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = trackingCode,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun DeliveryItemPreview() {
    Door2DoorAppTheme {
        Surface {
            DeliveryItemContent(
                "UG123123UG"
            )
        }
    }
}