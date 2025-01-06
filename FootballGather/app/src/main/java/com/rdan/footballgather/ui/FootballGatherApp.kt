package com.rdan.footballgather.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.screens.list.PlayerListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballGatherApp(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FootballGatherTopBar(
                scrollBehavior,
                modifier
            )
        }
    ) { innerPadding ->
        PlayerListScreen(
            contentPadding = innerPadding,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FootballGatherTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.players),
                style = MaterialTheme.typography.titleMedium
            )
        },
        modifier = modifier
    )
}