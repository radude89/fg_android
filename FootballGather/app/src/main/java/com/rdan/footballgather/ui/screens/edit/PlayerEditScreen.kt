package com.rdan.footballgather.ui.screens.edit

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.components.forms.PlayerEntryForm
import com.rdan.footballgather.ui.navigation.NavigationDestination
import com.rdan.footballgather.ui.screens.add.PlayerAddDestination
import kotlinx.coroutines.launch

object PlayerEditDestination : NavigationDestination {
    override val route = "player_edit"
    override val titleRes = R.string.edit_player_title
    const val PLAYER_ID_ARG = "playerId"
    val routeWithArgs = "$route/{$PLAYER_ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerEditScreen(
    navigateBack: () -> Unit,
    viewModel: PlayerEditViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(PlayerAddDestination.titleRes),
                navigateBack = navigateBack
            )
        }
    ) { innerPadding ->
        PlayerEntryForm(
            uiState = viewModel.playerEntryUiState,
            onPlayerEntryValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePlayer()
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