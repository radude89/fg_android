package com.rdan.footballgather.ui.screens.gather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.rdan.footballgather.model.Team
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination

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
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.gather),
                scrollBehavior = scrollBehavior,
                navigateBack = navigateBack
            )
        }
    ) { contentPadding ->
        ColumnView(
            uiState = uiState,
            contentPadding = contentPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun ColumnView(
    uiState: GatherUiState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_mediumLarge)
        )
    ) {
        sectionContent(
            team = Team.TeamA,
            players = uiState.teamAPlayers,
            modifier = modifier
        )
        sectionContent(
            team = Team.TeamB,
            players = uiState.teamBPlayers,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.sectionContent(
    team: Team,
    players: List<Player>,
    modifier: Modifier
) {
    stickyHeader {
        SectionTitle(
            team = team,
            modifier = modifier
        )
    }
    items(players) { player ->
        RowItem(
            title = player.name,
            modifier = modifier
        )
    }
}

@Composable
private fun SectionTitle(
    team: Team,
    modifier: Modifier
) {
    Text(
        text = Team.toDisplayName(team) ?: "-",
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_mediumLarge),
            )
    )
}

@Composable
private fun RowItem(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        title,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_mediumLarge),
            )
    )
}