package com.rdan.footballgather.ui.screens.confirm

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination

object PlayersConfirmationDestination : NavigationDestination {
    override val route = "players_confirmation"
    override val titleRes = R.string.confirm_players
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersConfirmationScreen(
    navigateBack: () -> Unit,
    viewModel: PlayersConfirmationViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val playerListUiState by viewModel.playersConfirmationListUiState.collectAsState()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.confirm_players),
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            ConfirmFloatingButton(
                onClick = {},
                modifier = modifier
            )
        }
    ) { contentPadding ->
        ColumnView(
            players = playerListUiState.playerList,
            contentPadding = contentPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun ConfirmFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_large))

    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(R.string.confirm_players_action),
        )
    }
}

@Composable
private fun ColumnView(
    players: List<Player>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_mediumLarge)
        )
    ) {
        items(players) { player ->
            PlayerItem(
                player,
                modifier
            )
        }
    }
}

@Composable
private fun PlayerItem(
    player: Player,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            CardTitle(player)
            CardItem(player)
        }
    }
}

@Composable
private fun CardTitle(
    player: Player,
    modifier: Modifier = Modifier
) {
    val playerTeams = remember { mutableStateMapOf<Player, String>() }
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            player.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_small),
                )
        )
        Box {
            Button(onClick = { expanded = true }) {
                Text(text = playerTeams[player] ?: "Select Team")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOf("Team A", "Team B", "Bench").forEach { team ->
                    DropdownMenuItem(
                        text = { Text(team) },
                        onClick = {
                            playerTeams[player] = team
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CardItem(
    player: Player,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_small)
        )
    ) {
        CardRowItem(
            textItemID = R.string.position_label,
            textItem = player.position?.name ?: "-",
            modifier
        )
        CardRowItem(
            textItemID = R.string.skill_label,
            textItem = player.skill?.name ?: "-",
            modifier
        )
    }
}

@Composable
private fun CardRowItem(
    @StringRes textItemID: Int,
    textItem: String,
    modifier: Modifier = Modifier
) {
    val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
    val annotatedString = buildAnnotatedString {
        withStyle(boldStyle) {
            append(stringResource(textItemID))
        }
        append(" ")
        append(textItem)
    }
    Row(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}