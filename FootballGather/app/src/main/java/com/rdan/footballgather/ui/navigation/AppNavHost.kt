package com.rdan.footballgather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rdan.footballgather.ui.screens.addedit.PlayerAddEntryDestination
import com.rdan.footballgather.ui.screens.addedit.PlayerAddEntryScreen
import com.rdan.footballgather.ui.screens.details.PlayerDetailsDestination
import com.rdan.footballgather.ui.screens.details.PlayerDetailsScreen
import com.rdan.footballgather.ui.screens.list.PlayerListDestination
import com.rdan.footballgather.ui.screens.list.PlayerListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PlayerListDestination.route,
        modifier = modifier
    ) {
        composable(route = PlayerListDestination.route) {
            PlayerListScreen(
                navigateToPlayerUpdate = {
                    navController
                        .navigate("${PlayerDetailsDestination.route}/${it}")
                }
            )
        }
        composable(
            route = PlayerDetailsDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PlayerDetailsDestination.PLAYER_ID_ARG) {
                    type = NavType.LongType
                }
            )
        ) {
            PlayerDetailsScreen(
                navigateToEditPlayer = { TODO() },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = PlayerAddEntryDestination.route) {
            PlayerAddEntryScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}