package com.rdan.footballgather.ui.screens.gather.timer

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.rdan.footballgather.R

@Composable
fun SetTimeAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { AlertTitle() },
        text = { TimePickerScreen() },
        confirmButton = {
            AlertButton(
                titleID = R.string.confirm,
                fontWeight = FontWeight.Bold,
                onClick = onConfirm
            )
        },
        dismissButton = {
            AlertButton(
                titleID = R.string.dismiss,
                onClick = onConfirm
            )
        }
    )
}

@Composable
private fun AlertButton(
    @StringRes titleID: Int,
    fontWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .padding(end = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(titleID),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = fontWeight
        )
    }
}

@Composable
private fun AlertTitle() {
    Text(
        text = stringResource(R.string.set_time),
        style = MaterialTheme.typography.titleMedium
    )
}