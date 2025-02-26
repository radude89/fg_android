package com.rdan.footballgather.ui.screens.gather.teamsplayers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team

@Composable
fun TeamsPlayersScreen(
    viewModel: TeamsPlayersViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_mediumLarge)
        ),
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth()
    ) {
        sectionContent(
            team = Team.TeamA,
            players = viewModel.teamAPlayers,
            modifier = modifier
        )
        sectionContent(
            team = Team.TeamB,
            players = viewModel.teamBPlayers,
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
        style = MaterialTheme.typography.bodyLarge,
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