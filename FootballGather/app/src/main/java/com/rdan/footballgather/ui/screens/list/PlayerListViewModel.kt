package com.rdan.footballgather.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rdan.footballgather.FootballGatherApplication
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayerListViewModel(
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    private val _players = MutableStateFlow<List<Player>>(emptyList())

    val players: StateFlow<List<Player>> = _players.asStateFlow()

    init {
        getAllPlayers()
    }

    private fun getAllPlayers() {
        viewModelScope.launch {
            playerRepository.getAllPlayers().collect { playerList ->
                _players.value = playerList
            }
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FootballGatherApplication)
                PlayerListViewModel(application.container.appRepository)
            }
        }
    }
}