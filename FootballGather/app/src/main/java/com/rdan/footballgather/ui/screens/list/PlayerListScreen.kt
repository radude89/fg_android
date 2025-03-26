package com.rdan.footballgather.ui.screens.list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination

object PlayerListDestination : NavigationDestination {
    override val route = "player_list"
    override val titleRes = R.string.players
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScreen(
    navigateToPlayerUpdate: (Long) -> Unit,
    navigateToAddPlayer: () -> Unit,
    navigateToConfirmPlayers: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerListViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val playerListUiState by viewModel.playerListUiState.collectAsState()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.players),
                scrollBehavior = scrollBehavior,
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            if (isLandscape) {
                FloatingActionButtonsInLandscape(
                    playerListUiState = playerListUiState,
                    onConfirm = navigateToConfirmPlayers,
                    onAdd = navigateToAddPlayer
                )
            } else {
                FloatingActionButtonsInPortrait(
                    playerListUiState = playerListUiState,
                    onConfirm = navigateToConfirmPlayers,
                    onAdd = navigateToAddPlayer
                )
            }
        }
    ) { contentPadding ->
        if (playerListUiState.playerList.isEmpty()) {
            EmptyPlayerList(modifier = modifier)
        } else {
            ColumnView(
                players = playerListUiState.playerList,
                contentPadding = contentPadding,
                onPlayerClick = { navigateToPlayerUpdate(it.id) },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun FloatingActionButtonsInLandscape(
    playerListUiState: PlayerUiState,
    onConfirm: () -> Unit,
    onAdd: () -> Unit
) {
    Row {
        if (playerListUiState.playerList.size > 1) {
            ConfirmPlayersButton(
                isLandscape = true,
                onClick = onConfirm
            )
        }
        AddButton(
            isLandscape = true,
            onClick = onAdd
        )
    }
}

@Composable
private fun FloatingActionButtonsInPortrait(
    playerListUiState: PlayerUiState,
    onConfirm: () -> Unit,
    onAdd: () -> Unit
) {
    Column {
        if (playerListUiState.playerList.size > 1) {
            ConfirmPlayersButton(
                isLandscape = false,
                onClick = onConfirm
            )
        }
        AddButton(
            isLandscape = false,
            onClick = onAdd
        )
    }
}

@Composable
private fun AddButton(
    isLandscape: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(
                dimensionResource(
                    if (isLandscape) R.dimen.padding_medium
                    else R.dimen.padding_small
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_player_title)
        )
    }
}

@Composable
private fun ConfirmPlayersButton(
    isLandscape: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonModifier = if (isLandscape)
        modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
    else
        modifier.padding(
            horizontal = dimensionResource(R.dimen.padding_small),
            vertical = dimensionResource(R.dimen.padding_mediumLarge),
        )
    FloatingActionButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        modifier = buttonModifier
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.confirm_players_title)
        )
    }
}

@Composable
private fun ColumnView(
    players: List<Player>,
    contentPadding: PaddingValues,
    onPlayerClick: (Player) -> Unit,
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
                onPlayerClick,
                modifier
            )
        }
    }
}

@Composable
private fun PlayerItem(
    player: Player,
    onPlayerClick: (Player) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .clickable { onPlayerClick(player) }
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
    Text(
        player.name,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
            )
    )
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

@Composable
private fun EmptyPlayerList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_players_available),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
        )
    }
}