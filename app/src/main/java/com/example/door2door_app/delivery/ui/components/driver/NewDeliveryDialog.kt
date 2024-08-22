package com.example.door2door_app.delivery.ui.components.driver

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.door2door_app.R

@Composable
fun NewDeliveryDialog(
    onDismissRequest: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.new_delivery)) },
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