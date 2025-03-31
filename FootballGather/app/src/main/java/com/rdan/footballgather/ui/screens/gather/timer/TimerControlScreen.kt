package com.rdan.footballgather.ui.screens.gather.timer

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.AppViewModelProvider
import com.rdan.footballgather.ui.components.alertdialogs.DefaultAlertDialog

@Composable
fun TimerControlScreen(
    modifier: Modifier = Modifier,
    viewModel: TimerControlViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    ),
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
    var showFinishedAlert by remember { mutableStateOf(false) }
    val notificationHandler = NotificationHandler()
    val context = LocalContext.current

    LaunchedEffects(
        viewModel = viewModel,
        onTimerFinished = {
            notificationHandler.showTimerFinishedNotification(context)
            showFinishedAlert = true
        }
    )

    TimerControlRowView(
        viewModel = viewModel,
        uiState = viewModel.uiState,
        onCancel = viewModel::onCancel,
        onStart = viewModel::onStartOrPause,
        onSetTime = { showTimePicker = true }
    )
    if (showTimePicker) {
        SetTimerAlertDialog(
            viewModel,
            onDismiss = { showTimePicker = false }
        )
    }
    if (showFinishedAlert) {
        GatherCompletedAlert { showFinishedAlert = false }
    }
}

@Composable
private fun LaunchedEffects(
    viewModel: TimerControlViewModel,
    onTimerFinished: () -> Unit
) {
    LaunchedEffect(viewModel.isRunning) {
        Log.d("FG - LaunchedEffect", "isRunning: ${viewModel.isRunning}")
        if (viewModel.isRunning) {
            viewModel.startCountdown()
        }
    }

    LaunchedEffect(viewModel.timerFinished) {
        Log.d("FG - LaunchedEffect", "timerFinished: ${viewModel.timerFinished}")
        if (viewModel.timerFinished) {
            onTimerFinished()
        }
    }
}

@Composable
private fun GatherCompletedAlert(
    onDismiss: () -> Unit
) {
    DefaultAlertDialog(
        titleTextID = R.string.notification_title,
        contentMessageID = R.string.notification_content,
        dismissButtonTitleID = R.string.ok,
        onDismissRequest = onDismiss
    )
}

@Composable
private fun SetTimerAlertDialog(
    viewModel: TimerControlViewModel,
    onDismiss: () -> Unit
) {
    SetTimeAlertDialog(
        initialMin = viewModel.remainingMin,
        initialSec = viewModel.remainingSec,
        onConfirm = { min, sec ->
            viewModel.setTime(min, sec)
            onDismiss()
        },
        onDismiss = onDismiss
    )
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