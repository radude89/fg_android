package com.rdan.footballgather.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.rdan.footballgather.R
import com.rdan.footballgather.model.PlayerPosition
import com.rdan.footballgather.model.PlayerSkill
import com.rdan.footballgather.ui.components.pickers.DefaultPicker
import com.rdan.footballgather.ui.components.textfields.DefaultOutlinedTextField

@Composable
fun PlayerEntryForm(
    uiState: PlayerEntryUiState,
    onPlayerEntryValueChange: (PlayerDetails) -> Unit,
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
    }
}

@Composable
private fun PlayerEntryInputForm(
    playerDetails: PlayerDetails,
    onValueChange: (PlayerDetails) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedSkill by remember(playerDetails) {
        val skill = playerDetails.skill?.let { PlayerSkill.valueOf(it) }
            ?: PlayerSkill.Unknown
        mutableStateOf(skill)
    }
    var selectedPosition by remember(playerDetails) {
        val position = playerDetails.position?.let { PlayerPosition.valueOf(it) }
            ?: PlayerPosition.Unknown
        mutableStateOf(position)
    }
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
        PlayerPositionPicker(
            selectedPosition = selectedPosition,
            onPositionSelected = { newPosition ->
                selectedPosition = newPosition
                onValueChange(playerDetails.copy(position = newPosition.name))
            }
        )
        PlayerSkillPicker(
            selectedSkill = selectedSkill,
            onSkillSelected = { newSkill ->
                selectedSkill = newSkill
                onValueChange(playerDetails.copy(skill = newSkill.name))
            }
        )
    }
}

@Composable
private fun PlayerEntryField(
    playerDetails: PlayerDetails,
    onValueChange: (PlayerDetails) -> Unit = {}
) {
    DefaultOutlinedTextField(
        textValue = playerDetails.name,
        labelID = R.string.player_name_required,
        onValueChange = {
            onValueChange(playerDetails.copy(name = it))
        }
    )
}

@Composable
private fun PlayerSkillPicker(
    selectedSkill: PlayerSkill,
    onSkillSelected: (PlayerSkill) -> Unit,
    modifier: Modifier = Modifier
) {
    DefaultPicker(
        labelTitleID = R.string.choose_skill,
        dropdownTitle = selectedSkill.name,
        dropdownEntries = PlayerSkill.entries.map { it.name },
        onDropdownItemSelected = { selectedEntry ->
            val skill = PlayerSkill.entries.find { it.name == selectedEntry }
                ?: PlayerSkill.Unknown
            onSkillSelected(skill)
        },
        modifier = modifier,
    )
}

@Composable
private fun PlayerPositionPicker(
    selectedPosition: PlayerPosition,
    onPositionSelected: (PlayerPosition) -> Unit,
    modifier: Modifier = Modifier
) {
    DefaultPicker(
        labelTitleID = R.string.choose_position,
        dropdownTitle = selectedPosition.name,
        dropdownEntries = PlayerPosition.entries.map { it.name },
        onDropdownItemSelected = { selectedEntry ->
            val position = PlayerPosition.entries.find { it.name == selectedEntry }
                ?: PlayerPosition.Unknown
            onPositionSelected(position)
        },
        modifier = modifier
    )
}