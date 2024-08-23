package com.example.door2door_app.main.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.door2door_app.MainActivity
import com.example.door2door_app.delivery.domain.model.DeliveryDialogInfo
import com.example.door2door_app.navigation.DeliveryDriverNavGraph
import com.example.door2door_app.navigation.bottom.BottomNavigationBar
import com.example.door2door_app.websockets.WebSocketClient

@Composable
fun DeliveryDriverScreen(
    webSocketClient: WebSocketClient,
    parentNavController: NavController,
    showDeliveryDialog: DeliveryDialogInfo = DeliveryDialogInfo(),
    onDismissDialog: () -> Unit = {}
) {
    val bottomBarNavController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomBarNavController)
        },
        content = { innerPadding ->
            DeliveryDriverNavGraph(
                modifier = Modifier.padding(paddingValues = innerPadding),
                parentNavController = parentNavController,
                webSocketClient = webSocketClient,
                navController = bottomBarNavController,
                showDeliveryDialog = showDeliveryDialog,
                onDismissDialog = onDismissDialog
            )
        }
    )
}