package com.rdan.footballgather.ui.screens.history

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.model.Gather
import com.rdan.footballgather.model.Team
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.FootballGatherTopBar
import com.rdan.footballgather.ui.navigation.NavigationDestination

object HistoryDestination : NavigationDestination {
    override val route = "history"
    override val titleRes = R.string.history
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: HistoryViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopBar(scrollBehavior, navigateBack) }
    ) { contentPadding ->
        ContentView(
            viewModel,
            uiState,
            modifier,
            contentPadding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navigateBack: () -> Unit,
) {
    FootballGatherTopBar(
        title = stringResource(R.string.history),
        scrollBehavior = scrollBehavior,
        navigateBack = navigateBack
    )
}

@Composable
private fun ContentView(
    viewModel: HistoryViewModel,
    uiState: HistoryUiState,
    modifier: Modifier,
    contentPadding: PaddingValues
) {
    if (uiState.gathers.isEmpty()) {
        EmptyView(modifier)
    } else {
        ColumnView(
            viewModel = viewModel,
            gathers = uiState.gathers,
            contentPadding = contentPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun ColumnView(
    viewModel: HistoryViewModel,
    gathers: List<Gather>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_mediumLarge)
        )
    ) {
        items(gathers) { gather ->
            GatherItem(viewModel, gather, modifier)
        }
    }
}

@Composable
private fun GatherItem(
    viewModel: HistoryViewModel,
    gather: Gather,
    modifier: Modifier = Modifier
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        MainItemContentView(viewModel, gather, modifier)
    }
}

@Composable
private fun MainItemContentView(
    viewModel: HistoryViewModel,
    gather: Gather,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.padding_mediumLarge),
                vertical = dimensionResource(R.dimen.padding_large)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainItemLeadingView(
            viewModel = viewModel,
            gather = gather,
            modifier = Modifier.weight(1f)
        )
        HorizontalSpacer()
        ScoreView(
            gather = gather,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
private fun MainItemLeadingView(
    viewModel: HistoryViewModel,
    gather: Gather,
    modifier: Modifier
) {
    Column(modifier) {
        TeamPlayersView(viewModel, Team.TeamA, gather)
        StaticTextView(R.string.vs)
        TeamPlayersView(viewModel, Team.TeamB, gather)
    }
}

@Composable
private fun TeamPlayersView(
    viewModel: HistoryViewModel,
    team: Team,
    gather: Gather,
    modifier: Modifier = Modifier
) {
    Text(
        text = viewModel.getPlayerLine(team, gather),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(
            bottom = dimensionResource(R.dimen.padding_small)
        )
    )
}

@Composable
private fun StaticTextView(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(textRes),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(
            bottom = dimensionResource(R.dimen.padding_small)
        )
    )
}

@Composable
private fun HorizontalSpacer() {
    Spacer(
        modifier = Modifier.padding(
            horizontal = dimensionResource(R.dimen.padding_small)
        )
    )
}

@Composable
private fun ScoreView(
    gather: Gather,
    modifier: Modifier = Modifier
) {
    Text(
        text = gather.score,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
    )
}

@Composable
private fun EmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_gathers_available),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
        )
    }
}