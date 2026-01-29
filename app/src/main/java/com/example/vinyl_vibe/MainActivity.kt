package com.example.vinyl_vibe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vinyl_vibe.navigation.AppNavigation
import com.example.vinyl_vibe.ui.theme.Vinyl_VibeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        com.example.vinyl_vibe.ui.theme.Vinyl_VibeTheme {
            AppNavigation()
        }
    }
    }
}
//actividad limpia
//configura el tema y llama a navegacion