package com.rdan.footballgather.ui.screens.add

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

object PlayerAddDestination : NavigationDestination {
    override val route = "player_entry"
    override val titleRes = R.string.players_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerAddScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: PlayerAddViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(PlayerAddDestination.titleRes),
                navigateBack = navigateBack
            )
        },
        floatingActionButton = {
            SaveFloatingButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.savePlayer()
                        navigateBack()
                    }
                }
            )
        }
    ) { innerPadding ->
        PlayerEntryForm(
            uiState = viewModel.playerEntryUiState,
            onPlayerEntryValueChange = viewModel::updateUiState,
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
private fun SaveFloatingButton(
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
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.save_action),
        )
    }
}