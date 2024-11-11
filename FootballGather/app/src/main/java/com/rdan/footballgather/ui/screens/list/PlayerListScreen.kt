package com.rdan.footballgather.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Player

@Composable
fun PlayerListScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewModel: PlayerListViewModel = viewModel(
        factory = PlayerListViewModel.factory
    )
    val players: List<Player> by viewModel.players.collectAsState()
    ColumnView(
        players = players,
        contentPadding = contentPadding,
        modifier = modifier
    )
}

@Composable
private fun ColumnView(
    players: List<Player>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (contentPadding = contentPadding) {
        items(players) {
            Card(
                modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Column(
                    Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        it.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        it.name,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}