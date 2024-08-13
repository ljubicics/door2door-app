package com.example.door2door_app.delivery.ui.components

import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.door2door_app.delivery.domain.model.Delivery
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.user.domain.model.Account


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeliveriesView(
    modifier: Modifier = Modifier,
    deliveries: List<Delivery>,
    account: Account? = null
) {

    val list = mutableListOf<Delivery>().apply {
        repeat(10) {
            add(
                Delivery(
                    trackingCode = "UG123123UG"
                )
            )
        }
    }

    val listState = rememberLazyListState()
    val isHeaderAtTop by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (visibleItemsInfo.isNotEmpty()) {
                val firstVisibleItem = visibleItemsInfo.first()
                firstVisibleItem.index == 1 && firstVisibleItem.offset == 0
            } else {
                false
            }
        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(
                        if (isHeaderAtTop) RoundedCornerShape(0.dp) else RoundedCornerShape(
                            topStart = 40.dp,
                            topEnd = 40.dp
                        )
                    )
                    .background(color = MaterialTheme.colorScheme.surface),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(paddingValues = PaddingValues(start = 16.dp)),
                    text = "Finished deliveries",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        itemsIndexed(items = deliveries, key = { index, _ -> index }) { index, delivery ->
            DeliveryItem(
                trackingCode = delivery.trackingCode ?: "UG123123UG"
            )
        }
        item {
            Box(modifier = Modifier
                .height(80.dp)
                .background(color = MaterialTheme.colorScheme.surface)
            )
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
