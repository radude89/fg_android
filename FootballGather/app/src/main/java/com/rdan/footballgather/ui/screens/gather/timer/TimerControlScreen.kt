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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    modifier: Modifier = Modifier,
    viewModel: TimerControlViewModel = TimerControlViewModel()
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
    val uiState = viewModel.uiState

    LaunchedEffect(viewModel.isRunning) {
        if (viewModel.isRunning) {
            viewModel.startCountdown()
        }
    }

    TimerControlRowView(
        viewModel = viewModel,
        uiState = uiState,
        onCancel = viewModel::onCancel,
        onStart = viewModel::onStartOrPause,
        onSetTime = { showTimePicker = true }
    )
    if (showTimePicker) {
        SetTimeAlertDialog(
            initialMin = viewModel.remainingMin,
            initialSec = viewModel.remainingSec,
            onConfirm = { min, sec ->
                viewModel.setTime(min, sec)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Composable
private fun TimerControlRowView(
    viewModel: TimerControlViewModel,
    uiState: TimerControlUiState,
    onCancel: () -> Unit,
    onStart: () -> Unit,
    onSetTime: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimerControlRowContentView(
            viewModel = viewModel,
            uiState = uiState,
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
    viewModel: TimerControlViewModel,
    uiState: TimerControlUiState,
    onCancel: () -> Unit,
    onStart: () -> Unit
) {
    TimerControlButton(
        title = R.string.cancel,
        onClick = onCancel,
        enabled = uiState.isCancelEnabled
    )
    HorizontalSpacer()
    TimeView(viewModel)
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
    viewModel: TimerControlViewModel
) {
    val formattedMinutes = viewModel.formattedRemainingMin
    val formattedSeconds = viewModel.formattedRemainingSec
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