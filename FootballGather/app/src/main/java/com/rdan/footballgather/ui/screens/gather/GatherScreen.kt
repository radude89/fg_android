package com.rdan.footballgather.ui.screens.gather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination
import com.rdan.footballgather.ui.screens.gather.score.ScoreScreen
import com.rdan.footballgather.ui.screens.gather.teamsplayers.TeamsPlayersScreen
import com.rdan.footballgather.ui.screens.gather.teamsplayers.TeamsPlayersViewModel
import com.rdan.footballgather.ui.screens.gather.timer.TimerControlScreen

object GatherDestination : NavigationDestination {
    override val route = "gather"
    override val titleRes = R.string.gather
    const val PLAYER_TEAMS_ARG = "playerTeams"
    val routeWithArgs = "$route/{$PLAYER_TEAMS_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GatherScreen(
    navigateBack: () -> Unit,
    viewModel: GatherViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.gather),
                scrollBehavior = scrollBehavior,
                navigateBack = navigateBack
            )
        }
    ) { contentPadding ->
        ContentView(
            teamAPlayers = uiState.teamAPlayers,
            teamBPlayers = uiState.teamBPlayers,
            contentPadding = contentPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun ContentView(
    teamAPlayers: List<Player>,
    teamBPlayers: List<Player>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        )
    ) {
        ScoreScreen(
            innerPadding = contentPadding,
            modifier = modifier
        )
        TimerControlScreen(modifier = modifier)
        TeamsPlayersScreen(
            viewModel = TeamsPlayersViewModel(
                teamAPlayers = teamAPlayers,
                teamBPlayers = teamBPlayers
            ),
            modifier = modifier
        )
    }
}
