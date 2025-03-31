package com.rdan.footballgather.ui.screens.gather.score

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R

@Composable
fun ScoreScreen(
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel = viewModel(),
    innerPadding: PaddingValues,
    onScoreChanged: (String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(
                start = innerPadding
                    .calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding
                    .calculateEndPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding()
            )
            .verticalScroll(rememberScrollState())
    ) {
        ContentView(
            viewModel = viewModel,
            onScoreChanged = onScoreChanged
        )
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier,
    viewModel: ScoreViewModel,
    onScoreChanged: (String) -> Unit
) {
    val teamAScore by viewModel.teamAScore.collectAsState()
    val teamBScore by viewModel.teamBScore.collectAsState()
    LaunchedEffect(teamAScore) {
        onScoreChanged("$teamAScore:$teamBScore")
    }
    LaunchedEffect(teamBScore) {
        onScoreChanged("$teamAScore:$teamBScore")
    }
    Row(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TeamScoreColumn(
            teamName = stringResource(R.string.team_A),
            score = teamAScore,
            onIncrement = viewModel::incrementTeamA,
            onDecrement = viewModel::decrementTeamA
        )
        TeamScoreColumn(
            teamName = stringResource(R.string.team_B),
            score = teamBScore,
            onIncrement = viewModel::incrementTeamB,
            onDecrement = viewModel::decrementTeamB
        )
    }
}

@Composable
fun TeamScoreColumn(
    teamName: String,
    score: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        )
    ) {
        Text(
            text = teamName,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = score.toString(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        ButtonsRowView(
            onIncrement = onIncrement,
            onDecrement = onDecrement
        )
    }
}

@Composable
private fun ButtonsRowView(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_verySmall)
        )
    ) {
        IncrementDecrementButton(
            titleID = R.string.minus,
            onClick = onDecrement
        )
        IncrementDecrementButton(
            titleID = R.string.plus,
            onClick = onIncrement
        )
    }
}

@Composable
private fun IncrementDecrementButton(
    @StringRes titleID: Int,
    onClick: () -> Unit
) {
    Button(onClick) {
        Text(
            text = stringResource(titleID),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}