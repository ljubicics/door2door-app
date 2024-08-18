package com.example.door2door_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.door2door_app.navigation.AppNavigation
import com.example.door2door_app.ui.theme.Door2DoorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Door2DoorAppTheme {
                Surface {
                    WindowCompat.getInsetsController(window, LocalView.current).isAppearanceLightStatusBars = true
                    AppNavigation()
                }
            }
        }
    }
}