package com.rdan.footballgather.ui.screens.confirm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
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
    Scaffold(
        topBar = {
            FootballGatherTopBar(
                title = stringResource(R.string.confirm_players),
                navigateBack = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        PlayerListContentView(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PlayerListContentView(
    modifier: Modifier = Modifier
) {
    val players = listOf("Radu", "John", "Jane")
    val playerTeams = remember { mutableStateMapOf<String, String>() }

    Column(modifier = Modifier.padding(16.dp)) {
        players.forEach { player ->
            var expanded by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = player, modifier = Modifier.weight(1f))
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
    }
}
