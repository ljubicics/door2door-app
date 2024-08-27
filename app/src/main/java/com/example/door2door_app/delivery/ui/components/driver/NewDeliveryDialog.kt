package com.example.door2door_app.delivery.ui.components.driver

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.door2door_app.R
import com.example.door2door_app.ui.theme.Door2DoorAppTheme

@Composable
fun NewDeliveryDialog(
    onDismissRequest: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.new_delivery)
            )
        },
        text = { Text(stringResource(R.string.do_you_want_to_accept_the_new_delivery)) },
        confirmButton = {
            TextButton(onClick = {
                onAccept()
                onDismissRequest()
            }) {
                Text(stringResource(R.string.accept))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onReject()
                onDismissRequest()
            }) {
                Text(stringResource(R.string.reject))
            }
        }
    )
}

@Preview
@Composable
fun PreviewNewDeliveryDialog() {
    Door2DoorAppTheme {
        Surface {
            NewDeliveryDialog(
                onDismissRequest = {},
                onAccept = {},
                onReject = {}
            )
        }
    }
}