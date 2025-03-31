package com.rdan.footballgather.ui.screens.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.StringRes
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.PlayerPosition
import com.rdan.footballgather.model.PlayerSkill
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.components.alertdialogs.DefaultAlertDialog
import com.rdan.footballgather.ui.components.forms.toPlayer
import com.rdan.footballgather.ui.navigation.NavigationDestination
import kotlinx.coroutines.CoroutineScope
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
    viewModel: PlayerDetailsViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.player_details),
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButtonsView(
                uiState = uiState,
                coroutineScope = coroutineScope,
                viewModel = viewModel,
                navigateBack = navigateBack,
                navigateToEditPlayer = navigateToEditPlayer
            )
        },
        modifier = modifier
    ) { innerPadding ->
        PlayerDetailsContent(
            playerDetailsUiState = uiState.value,
            viewModel = viewModel,
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
private fun FloatingActionButtonsView(
    uiState: State<PlayerDetailsUiState>,
    coroutineScope: CoroutineScope,
    viewModel: PlayerDetailsViewModel,
    navigateBack: () -> Unit,
    navigateToEditPlayer: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        LandscapeOrientationButtonsView(
            onDelete = { deleteConfirmationRequired = true },
            onEdit = { navigateToEditPlayer(uiState.value.playerDetails.id) }
        )
    } else {
        PortraitOrientationButtonsView(
            onDelete = { deleteConfirmationRequired = true },
            onEdit = { navigateToEditPlayer(uiState.value.playerDetails.id) }
        )
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationAlert(
            modifier = modifier,
            onConfirm = {
                deleteConfirmationRequired = false
                coroutineScope.launch {
                    viewModel.deletePlayer()
                    navigateBack()
                }
            },
            onDismiss = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
private fun DeleteConfirmationAlert(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    DefaultAlertDialog(
        contentMessageID = R.string.delete_question,
        dismissButtonTitleID = R.string.no,
        confirmButtonTitleID = R.string.yes,
        onDismissRequest = onDismiss,
        onConfirmRequest = onConfirm,
        modifier = modifier
    )
}

@Composable
private fun LandscapeOrientationButtonsView(
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Row {
        DeleteFloatingButton(
            isLandscape = true,
            onClick = onDelete
        )
        EditFloatingButton(
            isLandscape = true,
            onClick = onEdit
        )
    }
}

@Composable
private fun PortraitOrientationButtonsView(
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Column {
        DeleteFloatingButton(
            isLandscape = false,
            onClick = onDelete
        )
        EditFloatingButton(
            isLandscape = false,
            onClick = onEdit
        )
    }
}

@Composable
private fun EditFloatingButton(
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
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(R.string.edit_player_title),
        )
    }
}

@Composable
private fun DeleteFloatingButton(
    isLandscape: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonModifier = if (isLandscape) {
        modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
    } else {
        modifier.padding(
            horizontal = dimensionResource(R.dimen.padding_small),
            vertical = dimensionResource(R.dimen.padding_mediumLarge),
        )
    }
    FloatingActionButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        modifier = buttonModifier
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_player_title),
        )
    }
}

@Composable
private fun PlayerDetailsContent(
    playerDetailsUiState: PlayerDetailsUiState,
    viewModel: PlayerDetailsViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        )
    ) {
        PlayerDetails(
            player = playerDetailsUiState.playerDetails.toPlayer(),
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth()
        )
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
                playerDetailsValue = when (player.position?.name) {
                    PlayerPosition.Unknown.name, null -> "-"
                    else -> player.position.name
                }
            )
            PlayerDetailsRow(
                labelResourceId = R.string.player_details_card_skill,
                playerDetailsValue = when (player.skill?.name) {
                    PlayerSkill.Unknown.name, null -> "-"
                    else -> player.skill.name
                }
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