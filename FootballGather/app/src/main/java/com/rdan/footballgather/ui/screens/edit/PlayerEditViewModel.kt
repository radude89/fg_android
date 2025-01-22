package com.rdan.footballgather.ui.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.ui.components.forms.PlayerDetails
import com.rdan.footballgather.ui.components.forms.PlayerEntryUiState
import com.rdan.footballgather.ui.components.forms.toPlayer
import com.rdan.footballgather.ui.components.forms.toPlayerEntryUiState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlayerEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    var playerEntryUiState by mutableStateOf(PlayerEntryUiState())
        private set

    private val playerId: Long = checkNotNull(
        savedStateHandle[PlayerEditDestination.PLAYER_ID_ARG]
    )

    init {
        viewModelScope.launch {
            playerEntryUiState = playerRepository.getPlayer(playerId)
                .filterNotNull()
                .first()
                .toPlayerEntryUiState(true)
        }
    }

    fun updateUiState(playerDetails: PlayerDetails) {
        playerEntryUiState = PlayerEntryUiState(
            playerDetails = playerDetails,
            isEntryValid = validateInput(playerDetails)
        )
    }

    suspend fun updatePlayer() {
        if (validateInput(playerEntryUiState.playerDetails)) {
            playerRepository.updatePlayer(
                playerEntryUiState.playerDetails.toPlayer()
            )
        }
    }

    private fun validateInput(
        playerDetails: PlayerDetails = playerEntryUiState.playerDetails
    ): Boolean {
        return playerDetails.name.isNotBlank()
    }
}