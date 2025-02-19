package com.rdan.footballgather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rdan.footballgather.ui.screens.add.PlayerAddDestination
import com.rdan.footballgather.ui.screens.add.PlayerAddScreen
import com.rdan.footballgather.ui.screens.confirm.PlayersConfirmationDestination
import com.rdan.footballgather.ui.screens.confirm.PlayersConfirmationScreen
import com.rdan.footballgather.ui.screens.details.PlayerDetailsDestination
import com.rdan.footballgather.ui.screens.details.PlayerDetailsScreen
import com.rdan.footballgather.ui.screens.edit.PlayerEditDestination
import com.rdan.footballgather.ui.screens.edit.PlayerEditScreen
import com.rdan.footballgather.ui.screens.gather.GatherDestination
import com.rdan.footballgather.ui.screens.gather.GatherScreen
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
                },
                navigateToAddPlayer = {
                    navController
                        .navigate(PlayerAddDestination.route)
                },
                navigateToConfirmPlayers = {
                    navController
                        .navigate(PlayersConfirmationDestination.route)
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
                navigateToEditPlayer = {
                    navController
                        .navigate("${PlayerEditDestination.route}/${it}")
                },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = PlayerAddDestination.route) {
            PlayerAddScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = PlayerEditDestination.routeWithArgs,
            arguments = listOf(
                navArgument(PlayerEditDestination.PLAYER_ID_ARG) {
                    type = NavType.LongType
                }
            )
        ) {
            PlayerEditScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(route = PlayersConfirmationDestination.route) {
            PlayersConfirmationScreen(
                navigateToGatherScreen = { playerTeams ->
                    navController.navigate("${GatherDestination.route}/$playerTeams")
                },
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = GatherDestination.routeWithArgs,
            arguments = listOf(
                navArgument(GatherDestination.PLAYER_TEAMS_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            GatherScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}