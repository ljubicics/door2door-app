package com.example.door2door_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.door2door_app.delivery.domain.model.DeliveryDialogInfo
import com.example.door2door_app.navigation.AppNavigation
import com.example.door2door_app.ui.theme.Door2DoorAppTheme
import com.example.door2door_app.websockets.WebSocketClient
import com.example.door2door_app.websockets.listener.WebSocketListener

class MainActivity : ComponentActivity(), WebSocketListener {

    private val webSocketClient = WebSocketClient("ws://188.245.93.77:8080/ws")
    private var dialogInfo by mutableStateOf(DeliveryDialogInfo())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Door2DoorAppTheme {
                Surface {
                    WindowCompat.getInsetsController(window, LocalView.current).isAppearanceLightStatusBars = true
                    AppNavigation(
                        webSocketClient = webSocketClient,
                        showDeliveryDialog = dialogInfo,
                        onDismissDialog = {
                            dialogInfo = dialogInfo.copy(
                                showDialog = false
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onConnected() {
        Log.d("websocket", "Connected")
    }

    override fun onMessage(message: String) {
        val id = message.split(" ")[1]
        dialogInfo = DeliveryDialogInfo(
            deliveryId = id.toLong(),
            showDialog = true
        )
    }

    override fun onDisconnected() {
        Log.d("websocket", "Disconnected")
    }
}