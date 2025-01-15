package com.rdan.footballgather.ui.screens.details

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object PlayerDetailsDestination : NavigationDestination {
    override val route = "player_details"
    override val titleRes = R.string.player_details
    const val PLAYER_ID_ARG = "playerId"
    val routeWithArgs = "$route/{$PLAYER_ID_ARG}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerDetailsScreen(
    navigateBack: () -> Unit,
    navigateToEditPlayer: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.player_details),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditPlayer(uiState.value.playerDetails.id) },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_item_title),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        PlayerDetailsContent(
            playerDetailsUiState = uiState.value,
            viewModel = viewModel,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deletePlayer()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding
                        .calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding
                        .calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding
                        .calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun PlayerDetailsContent(
    playerDetailsUiState: PlayerDetailsUiState,
    viewModel: PlayerDetailsViewModel,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        )
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        PlayerDetails(
            player = playerDetailsUiState.playerDetails.toPlayer(),
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(R.dimen.padding_large)
                )
        ) {
            Text(
                stringResource(R.string.delete),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Composable
private fun PlayerDetails(
    player: Player,
    viewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_large),
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_small)
            )
        ) {
            PlayerDetailsRow(
                labelResourceId = R.string.player_details_card_name,
                playerDetailsValue = player.name
            )
            PlayerDetailsRow(
                labelResourceId = R.string.player_details_card_position,
                playerDetailsValue = player.position?.name ?: "-"
            )
            PlayerDetailsRow(
                labelResourceId = R.string.player_details_card_skill,
                playerDetailsValue = player.skill?.name ?: "-"
            )
            PlayerDetailsRow(
                labelResourceId = R.string.player_details_card_created_at,
                playerDetailsValue = viewModel
                    .formattedPlayerCreationDate(player.createdAt)
            )
        }
    }
}

@Composable
private fun PlayerDetailsRow(
    @StringRes labelResourceId: Int,
    playerDetailsValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(labelResourceId),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = playerDetailsValue,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(stringResource(R.string.attention))
        },
        text = {
            Text(stringResource(R.string.delete_question))
       },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}