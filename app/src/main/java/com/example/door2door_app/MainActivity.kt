package com.example.door2door_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.door2door_app.navigation.AppNavigation
import com.example.door2door_app.ui.theme.Door2DoorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Door2DoorAppTheme {
                AppNavigation()
            }
        }
    }
}