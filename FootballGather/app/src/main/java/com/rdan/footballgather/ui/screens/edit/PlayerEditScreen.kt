package com.rdan.footballgather.ui.screens.edit

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.components.forms.PlayerEntryForm
import com.rdan.footballgather.ui.navigation.NavigationDestination
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
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: PlayerEditViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(PlayerEditDestination.titleRes),
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            SaveFloatingButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updatePlayer()
                        navigateBack()
                    }
                }
            )
        }
    ) { innerPadding ->
        PlayerEditContentView(
            viewModel = viewModel,
            contentPadding = innerPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun SaveFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
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
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.save_action),
        )
    }
}

@Composable
private fun PlayerEditContentView(
    viewModel: PlayerEditViewModel,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    PlayerEntryForm(
        uiState = viewModel.playerEntryUiState,
        onPlayerEntryValueChange = viewModel::updateUiState,
        modifier = modifier
            .padding(
                start = contentPadding
                    .calculateStartPadding(LocalLayoutDirection.current),
                end = contentPadding
                    .calculateEndPadding(LocalLayoutDirection.current),
                top = contentPadding.calculateTopPadding()
            )
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    )
}