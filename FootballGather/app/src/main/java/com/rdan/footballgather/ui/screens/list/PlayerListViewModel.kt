package com.rdan.footballgather.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class PlayerUiState(val playerList: List<Player> = listOf())

class PlayerListViewModel(
    playerRepository: FootballGatherRepository
) : ViewModel() {
    val playerListUiState: StateFlow<PlayerUiState> =
        playerRepository.getAllPlayers().map { PlayerUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlayerUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}