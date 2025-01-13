package com.rdan.footballgather.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rdan.footballgather.ui.navigation.AppNavHost

@Composable
fun FootballGatherApp(
    navController: NavHostController = rememberNavController()
) {
    AppNavHost(navController)
}