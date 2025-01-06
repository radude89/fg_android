package com.rdan.footballgather.ui.screens.list

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
    LazyColumn (
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_mediumLarge)
        )
    ) {
        items(players) { player ->
            Card(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                Column(
                    Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    CardTitle(player)
                    CardItem(player)
                }
            }
        }
    }
}

@Composable
private fun CardTitle(
    player: Player,
    modifier: Modifier = Modifier
) {
    Text(
        player.name,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
            )
    )
}

@Composable
private fun CardItem(
    player: Player,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.padding_small))
    ) {
        CardRowItem(
            textItemID = R.string.position_label,
            textItem = player.position.name,
            modifier
        )
        CardRowItem(
            textItemID = R.string.skill_label,
            textItem = player.skill.name,
            modifier
        )
    }
}

@Composable
private fun CardRowItem(
    @StringRes textItemID: Int,
    textItem: String,
    modifier: Modifier = Modifier
) {
    val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)
    val annotatedString = buildAnnotatedString {
        withStyle(boldStyle) {
            append(stringResource(textItemID))
        }
        append(" ")
        append(textItem)
    }
    Row(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small),
            )
    ) {
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}