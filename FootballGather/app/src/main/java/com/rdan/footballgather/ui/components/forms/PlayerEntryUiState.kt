package com.rdan.footballgather.ui.components.forms

data class PlayerEntryUiState(
    val playerDetails: PlayerDetails = PlayerDetails(),
    val isEntryValid: Boolean = false
)