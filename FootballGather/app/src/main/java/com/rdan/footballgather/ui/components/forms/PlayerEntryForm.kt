package com.rdan.footballgather.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rdan.footballgather.R
import com.rdan.footballgather.model.PlayerSkill
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
    var selectedSkill by remember { mutableStateOf(PlayerSkill.Unknown) }
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
        PlayerSkillPicker(
            selectedSkill = selectedSkill,
            onSkillSelected = { newSkill -> selectedSkill = newSkill }
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

@Composable
private fun PlayerSkillPicker(
    selectedSkill: PlayerSkill,
    onSkillSelected: (PlayerSkill) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(R.string.choose_skill),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = { expanded = true },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.typography.bodyMedium.color,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContentColor = MaterialTheme.typography.bodyMedium.color
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(R.dimen.padding_small)
                    )
            ) {
                Text(
                    text = selectedSkill.name
                        .replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        vertical = dimensionResource(R.dimen.padding_verySmall)
                    )
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = dimensionResource(R.dimen.padding_small))
        ) {
            PlayerSkill.entries.forEachIndexed { index, skill ->
                DropdownMenuItem(
                    onClick = {
                        onSkillSelected(skill)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = skill.name
                                .replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    modifier = Modifier
                        .padding(vertical = dimensionResource(R.dimen.padding_verySmall))
                )
                if (index < PlayerSkill.entries.size - 1) {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                        modifier = Modifier
                            .padding(start = dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}