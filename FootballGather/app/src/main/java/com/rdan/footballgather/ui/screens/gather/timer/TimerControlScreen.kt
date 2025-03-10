package com.rdan.footballgather.ui.screens.gather.timer

import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.rdan.footballgather.R

@Composable
fun TimerControlScreen(
    viewModel: TimerControlViewModel = TimerControlViewModel(),
    modifier: Modifier = Modifier
) {
    Card(modifier.fillMaxWidth()) {
        ContentView(
            viewModel = viewModel,
            modifier = modifier
        )
    }
}

@Composable
private fun ContentView(
    viewModel: TimerControlViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerControlMainView(viewModel)
    }
}

@Composable
private fun TimerControlMainView(
    viewModel: TimerControlViewModel
) {
    var showTimePicker by remember { mutableStateOf(false) }
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }
    val uiState = viewModel.uiState

    TimerControlRowView(
        uiState = uiState,
        selectedMin = minutes,
        selectedSec = seconds,
        onCancel = viewModel::onCancelClicked,
        onStart = viewModel::onStartOrPauseClicked,
        onSetTime = { showTimePicker = true }
    )
    if (showTimePicker) {
        SetTimeAlertDialog(
            initialMin = minutes,
            initialSec = seconds,
            onConfirm = { min, sec ->
                minutes = min
                seconds = sec
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Composable
private fun TimerControlRowView(
    uiState: TimerControlUiState,
    selectedMin: Int,
    selectedSec: Int,
    onCancel: () -> Unit,
    onStart: () -> Unit,
    onSetTime: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimerControlRowContentView(
            uiState = uiState,
            selectedMin = selectedMin,
            selectedSec = selectedSec,
            onCancel = onCancel,
            onStart = onStart
        )
    }
    VerticalSpacer()
    TimerControlButton(
        title = R.string.set_time,
        onClick = onSetTime,
        enabled = uiState.isSetTimeEnabled
    )
}

@Composable
private fun TimerControlRowContentView(
    uiState: TimerControlUiState,
    selectedMin: Int,
    selectedSec: Int,
    onCancel: () -> Unit,
    onStart: () -> Unit
) {
    TimerControlButton(
        title = R.string.cancel,
        onClick = onCancel,
        enabled = uiState.isCancelEnabled
    )
    HorizontalSpacer()
    TimeView(selectedMin, selectedSec)
    HorizontalSpacer()
    TimerControlButton(
        title = uiState.startButtonText,
        onClick = onStart
    )
}

@Composable
private fun TimerControlButton(
    @StringRes title: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun TimeView(
    minutes: Int,
    seconds: Int
) {
    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')
    Text(
        text = "$formattedMinutes:$formattedSeconds",
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun VerticalSpacer(
    @DimenRes padding: Int = R.dimen.padding_medium
) {
    Spacer(
        modifier = Modifier
            .height(dimensionResource(padding))
    )
}

@Composable
private fun HorizontalSpacer(
    @DimenRes padding: Int = R.dimen.padding_medium
) {
    Spacer(
        modifier = Modifier
            .width(dimensionResource(padding))
    )
}