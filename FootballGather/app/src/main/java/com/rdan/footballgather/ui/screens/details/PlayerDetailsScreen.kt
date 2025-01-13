package com.rdan.footballgather.ui.screens.details

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination

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
    modifier: Modifier = Modifier,
    viewModel: PlayerDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.player_details),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {
        Text("Player Details")
    }
}