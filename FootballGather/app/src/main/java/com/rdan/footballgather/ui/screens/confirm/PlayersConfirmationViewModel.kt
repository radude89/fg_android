package com.rdan.footballgather.ui.screens.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PlayersConfirmationUiState(val playerList: List<Player> = listOf())

class PlayersConfirmationViewModel(
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    val playersConfirmationListUiState: StateFlow<PlayersConfirmationUiState> =
        playerRepository.getAllPlayers()
            .map { PlayersConfirmationUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlayersConfirmationUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}