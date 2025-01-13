package com.rdan.footballgather.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PlayerDetailsUiState(
    val playerDetails: PlayerDetails = PlayerDetails()
)

class PlayerDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    playerRepository: FootballGatherRepository
) : ViewModel() {
    private val playerId: Long = checkNotNull(
        savedStateHandle[PlayerDetailsDestination.PLAYER_ID_ARG]
    )

    val uiState: StateFlow<PlayerDetailsUiState> =
        playerRepository.getPlayer(playerId)
            .filterNotNull()
            .map {
                PlayerDetailsUiState(
                    playerDetails = it.toPlayerDetails()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlayerDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}