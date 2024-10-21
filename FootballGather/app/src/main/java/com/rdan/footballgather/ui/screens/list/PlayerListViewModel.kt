package com.rdan.footballgather.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rdan.footballgather.FootballGatherApplication
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player

class PlayerListViewModel(
    private val playersRepository: FootballGatherRepository
) : ViewModel() {
    fun getPlayers(): List<Player> = playersRepository.getAllPlayers()

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FootballGatherApplication)
                val repository = application.container.appRepository
                PlayerListViewModel(repository)
            }
        }
    }
}