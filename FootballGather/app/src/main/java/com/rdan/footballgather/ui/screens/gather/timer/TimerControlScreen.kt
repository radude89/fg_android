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
    modifier: Modifier = Modifier
) {
    Card(modifier.fillMaxWidth()) {
        ContentView(modifier)
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayTimeRowView(
            onCancel = {},
            onStart = {},
            onSetTime = {
                showTimePicker = true
            }
        )
        if (showTimePicker) {
            SetTimeAlertDialog(
                onConfirm = { showTimePicker = false },
                onDismiss = { showTimePicker = false }
            )
        }
    }
}

@Composable
private fun DisplayTimeRowView(
    onCancel: () -> Unit,
    onStart: () -> Unit,
    onSetTime: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DisplayTimeRowContentView(onCancel, onStart)
    }
    VerticalSpacer()
    TimerControlButton(
        title = R.string.set_time,
        onClick = onSetTime
    )
}

@Composable
private fun DisplayTimeRowContentView(
    onCancel: () -> Unit,
    onStart: () -> Unit
) {
    TimerControlButton(
        title = R.string.cancel,
        onClick = onCancel,
        enabled = false
    )
    HorizontalSpacer()
    TimeView()
    HorizontalSpacer()
    TimerControlButton(
        title = R.string.start,
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
private fun TimeView() {
    Text(
        text = "00:00",
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