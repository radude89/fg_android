package com.rdan.footballgather.ui.screens.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.ui.screens.details.PlayerDetails
import com.rdan.footballgather.ui.screens.details.toPlayer

data class PlayerEntryUiState(
    val playerDetails: PlayerDetails = PlayerDetails(),
    val isEntryValid: Boolean = false
)

class PlayerAddEntryViewModel(
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

    suspend fun savePlayerEntry() {
        if (validateInput()) {
            playerRepository.insertPlayer(
                playerEntryUiState.playerDetails.toPlayer()
            )
        }
    }
}