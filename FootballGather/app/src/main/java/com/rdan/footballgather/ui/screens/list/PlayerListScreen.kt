package com.rdan.footballgather.ui.screens.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.model.Player

@Composable
fun PlayerListScreen(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewModel: PlayerListViewModel = viewModel(
        factory = PlayerListViewModel.factory
    )
    ColumnView(
        players = viewModel.getPlayers(),
        contentPadding = contentPadding
    )
}

@Composable
private fun ColumnView(
    players: List<Player>,
    contentPadding: PaddingValues
) {
    LazyColumn (contentPadding = contentPadding){
        items(players) {
            Text(
                it.name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}