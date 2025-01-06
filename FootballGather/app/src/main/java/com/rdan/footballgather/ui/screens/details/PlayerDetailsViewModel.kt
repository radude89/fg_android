package com.rdan.footballgather.ui.screens.details

import androidx.lifecycle.ViewModel
import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerDetailsViewModel(
    private val players: List<Player>
) : ViewModel() {
    private val _selectedPlayer = MutableStateFlow<Player?>(players.firstOrNull())
    val selectedPlayer: StateFlow<Player?> = _selectedPlayer.asStateFlow()

    fun setSelectedPlayer(player: Player) {
        _selectedPlayer.value = player
    }
}