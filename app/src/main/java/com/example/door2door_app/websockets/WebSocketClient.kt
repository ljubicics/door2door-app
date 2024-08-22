package com.example.door2door_app.websockets

import android.util.Log
import com.example.door2door_app.websockets.listener.WebSocketListener
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebSocketClient(private val url: String) {

    private val client = HttpClient {
        install(WebSockets)
    }

    fun connect(listener: WebSocketListener) {
        CoroutineScope(Dispatchers.IO).launch {
            client.wss(url) {
                listener.onConnected()

                try {
                    for (frame in incoming) {
                        if (frame is Frame.Text) {
                            Log.d("websocket", "Received frame: ${frame.readText()}")
                            listener.onMessage(frame.readText())
                        } else {
                            Log.d("websocket", "Received non-text frame")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("websocket", "Error: ${e.message}")
                }
            }
        }
    }

    fun disconnect(listener: WebSocketListener) {
        try {
            client.close()
            listener.onDisconnected()
            Log.d("websocket", "Connection closed")
        } catch (e: Exception) {
            Log.d("WebSocket", "Unable to close connection")
        }
    }
}