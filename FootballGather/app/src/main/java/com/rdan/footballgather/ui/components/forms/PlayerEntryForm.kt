package com.rdan.footballgather.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.rdan.footballgather.R
import com.rdan.footballgather.ui.components.PrimaryButton

@Composable
fun PlayerEntryForm(
    uiState: PlayerEntryUiState,
    onPlayerEntryValueChange: (PlayerDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(
                dimensionResource(R.dimen.padding_medium)
            ),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        PlayerEntryInputForm(
            playerDetails = uiState.playerDetails,
            onValueChange = onPlayerEntryValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        PrimaryButton(
            titleStringResID = R.string.save_action,
            enabled = uiState.isEntryValid,
            onClick = onSaveClick
        )
    }
}

@Composable
private fun PlayerEntryInputForm(
    playerDetails: PlayerDetails,
    onValueChange: (PlayerDetails) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement
            .spacedBy(
                dimensionResource(R.dimen.padding_medium)
            ),
        modifier = modifier
    ) {
        PlayerEntryField(
            playerDetails,
            onValueChange
        )
    }
}

@Composable
private fun PlayerEntryField(
    playerDetails: PlayerDetails,
    onValueChange: (PlayerDetails) -> Unit = {}
) {
    val fieldColor = MaterialTheme.colorScheme.secondaryContainer
    OutlinedTextField(
        value = playerDetails.name,
        onValueChange = {
            onValueChange(playerDetails.copy(name = it))
        },
        label = {
            Text(stringResource(R.string.player_name_required))
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = fieldColor,
            unfocusedContainerColor = fieldColor,
            disabledContainerColor = fieldColor,
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}