package com.rdan.footballgather.ui.screens.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.ui.components.forms.PlayerDetails
import com.rdan.footballgather.ui.components.forms.PlayerEntryUiState
import com.rdan.footballgather.ui.components.forms.toPlayer

class PlayerAddViewModel(
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    var playerEntryUiState by mutableStateOf(PlayerEntryUiState())
        private set

    fun updateUiState(playerDetails: PlayerDetails) {
        playerEntryUiState = PlayerEntryUiState(
            playerDetails = playerDetails,
            isEntryValid = validateInput(playerDetails)
        )
    }

    private fun validateInput(
        playerDetails: PlayerDetails = playerEntryUiState.playerDetails
    ): Boolean {
        return playerDetails.name.isNotBlank()
    }

    suspend fun savePlayer() {
        if (validateInput()) {
            playerRepository.insertPlayer(
                playerEntryUiState.playerDetails.toPlayer()
            )
        }
    }
}