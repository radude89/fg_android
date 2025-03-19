package com.rdan.footballgather.ui.components.alertdialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.rdan.footballgather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAlertDialog(
    modifier: Modifier = Modifier,
    @StringRes titleTextID: Int = R.string.attention,
    @StringRes contentMessageID: Int,
    @StringRes dismissButtonTitleID: Int = R.string.confirm,
    @StringRes confirmButtonTitleID: Int? = null,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (() -> Unit)? = null
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            AlertDialogContent(
                titleTextID = titleTextID,
                contentMessageID = contentMessageID,
                dismissButtonTitleID = dismissButtonTitleID,
                confirmButtonTitleID = confirmButtonTitleID,
                onDismissRequest = onDismissRequest,
                onConfirmRequest = onConfirmRequest
            )
        }
    }
}

@Composable
private fun AlertDialogContent(
    @StringRes titleTextID: Int = R.string.attention,
    @StringRes contentMessageID: Int,
    @StringRes dismissButtonTitleID: Int = R.string.confirm,
    @StringRes confirmButtonTitleID: Int? = null,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AlertDialogTitle(titleTextID)
        AlertDialogContent(contentMessageID)
        Spacer(
            modifier = Modifier
                .height(dimensionResource(R.dimen.padding_mediumLarge))
        )
        AlertDialogButtons(
            dismissButtonTitleID = dismissButtonTitleID,
            confirmButtonTitleID = confirmButtonTitleID,
            onDismissRequest = onDismissRequest,
            onConfirmRequest = onConfirmRequest
        )
    }
}

@Composable
private fun AlertDialogContent(
    @StringRes contentMessageID: Int
) {
    Text(
        text = stringResource(contentMessageID),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_small)
            )
    )
}

@Composable
private fun AlertDialogTitle(
    @StringRes titleTextID: Int
) {
    Text(
        text = stringResource(titleTextID),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(R.dimen.padding_smallMedium),
                horizontal = dimensionResource(R.dimen.padding_small)
            )
    )
}

@Composable
private fun AlertDialogButtons(
    @StringRes dismissButtonTitleID: Int,
    @StringRes confirmButtonTitleID: Int? = null,
    onDismissRequest: () -> Unit,
    onConfirmRequest: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        AlertDialogButton(
            buttonTitleID = dismissButtonTitleID,
            onClick = onDismissRequest
        )
        if (confirmButtonTitleID != null && onConfirmRequest != null) {
            AlertDialogButton(
                buttonTitleID = confirmButtonTitleID,
                onClick = onConfirmRequest
            )
        }
    }
}

@Composable
private fun AlertDialogButton(
    @StringRes buttonTitleID: Int,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .padding(end = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = stringResource(buttonTitleID),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}