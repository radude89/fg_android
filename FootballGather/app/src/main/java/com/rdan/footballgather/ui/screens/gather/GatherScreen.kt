package com.rdan.footballgather.ui.screens.gather

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.components.alertdialogs.DefaultAlertDialog
import com.rdan.footballgather.ui.navigation.NavigationDestination
import com.rdan.footballgather.ui.screens.gather.score.ScoreScreen
import com.rdan.footballgather.ui.screens.gather.teamsplayers.TeamsPlayersScreen
import com.rdan.footballgather.ui.screens.gather.teamsplayers.TeamsPlayersViewModel
import com.rdan.footballgather.ui.screens.gather.timer.TimerControlScreen

object GatherDestination : NavigationDestination {
    override val route = "gather"
    override val titleRes = R.string.gather
    const val PLAYER_TEAMS_ARG = "playerTeams"
    val routeWithArgs = "$route/{$PLAYER_TEAMS_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GatherScreen(
    modifier: Modifier = Modifier,
    viewModel: GatherViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffects()
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.gather),
                scrollBehavior = scrollBehavior,
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            StopGatherFloatingButton(
                onClick = {

                }
            )
        }
    ) { contentPadding ->
        ContentView(
            teamAPlayers = uiState.teamAPlayers,
            teamBPlayers = uiState.teamBPlayers,
            contentPadding = contentPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun DisposableEffects() {
    val context = LocalContext.current
    LockScreenOrientation(
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
        context = context
    )
    BackButtonEffect()
}

@Composable
fun LockScreenOrientation(orientation: Int, context: Context) {
    val activity = remember { context as? ComponentActivity }
    DisposableEffect(Unit) {
        val originalOrientation = activity?.requestedOrientation
        activity?.requestedOrientation = orientation
        onDispose {
            activity?.requestedOrientation = originalOrientation
                ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}

@Composable
private fun BackButtonEffect() {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner
        .current?.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current

    val backCallback = remember {
        object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do nothing, preventing back navigation
            }
        }
    }

    DisposableEffect(lifecycleOwner, onBackPressedDispatcher) {
        onBackPressedDispatcher?.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

@Composable
private fun StopGatherFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var confirmationRequired by rememberSaveable { mutableStateOf(false) }
    FloatingActionButton(
        onClick = { confirmationRequired = true },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(
                dimensionResource(R.dimen.padding_small)
            )
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = stringResource(R.string.end_gather),
        )
    }
    if (confirmationRequired) {
        EndGatherConfirmationAlert(
            modifier = modifier,
            onConfirm = {
                confirmationRequired = false
                onClick()
            },
            onDismiss = { confirmationRequired = false }
        )
    }
}

@Composable
private fun EndGatherConfirmationAlert(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    DefaultAlertDialog(
        contentMessageID = R.string.end_gather_questio,
        dismissButtonTitleID = R.string.no,
        confirmButtonTitleID = R.string.yes,
        onDismissRequest = onDismiss,
        onConfirmRequest = onConfirm,
        modifier = modifier
    )
}

@Composable
private fun ContentView(
    teamAPlayers: List<Player>,
    teamBPlayers: List<Player>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        )
    ) {
        ScoreScreen(
            innerPadding = contentPadding,
            modifier = modifier
        )
        TimerControlScreen(modifier = modifier)
        TeamsPlayersScreen(
            viewModel = TeamsPlayersViewModel(
                teamAPlayers = teamAPlayers,
                teamBPlayers = teamBPlayers
            ),
            modifier = modifier
        )
    }
}
