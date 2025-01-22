package com.rdan.footballgather.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.components.PrimaryButton
import com.rdan.footballgather.ui.navigation.NavigationDestination
import com.rdan.footballgather.ui.screens.details.PlayerDetails
import kotlinx.coroutines.launch

object PlayerAddDestination : NavigationDestination {
    override val route = "player_entry"
    override val titleRes = R.string.players_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerAddScreen(
    navigateBack: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: PlayerAddViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(PlayerAddDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        PlayerAddEntryContent(
            playerEntryUiState = viewModel.playerEntryUiState,
            onPlayerEntryValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.savePlayer()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(
                    start = innerPadding
                        .calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding
                        .calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
private fun PlayerAddEntryContent(
    playerEntryUiState: PlayerEntryUiState,
    onPlayerEntryValueChange: (PlayerDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(
                dimensionResource(R.dimen.padding_medium)
            ),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        PlayerEntryInputForm(
            playerDetails = playerEntryUiState.playerDetails,
            onValueChange = onPlayerEntryValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        PrimaryButton(
            titleStringResID = R.string.save_action,
            enabled = playerEntryUiState.isEntryValid,
            onClick = onSaveClick
        )
    }
}

@Composable
private fun PlayerEntryInputForm(
    playerDetails: PlayerDetails,
    onValueChange: (PlayerDetails) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(
                dimensionResource(R.dimen.padding_medium)
            ),
        modifier = modifier
    ) {
        PlayerEntryField(
            playerDetails,
            onValueChange
        )
    }
}

@Composable
private fun PlayerEntryField(
    playerDetails: PlayerDetails,
    onValueChange: (PlayerDetails) -> Unit = {}
) {
    val fieldColor = MaterialTheme.colorScheme.secondaryContainer
    OutlinedTextField(
        value = playerDetails.name,
        onValueChange = {
            onValueChange(playerDetails.copy(name = it))
        },
        label = {
            Text(stringResource(R.string.player_name_required))
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = fieldColor,
            unfocusedContainerColor = fieldColor,
            disabledContainerColor = fieldColor,
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}