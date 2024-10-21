package com.rdan.footballgather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rdan.footballgather.ui.theme.FootballGatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FootballGatherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FootballGatherApp()
                }
            }
        }
    }
}