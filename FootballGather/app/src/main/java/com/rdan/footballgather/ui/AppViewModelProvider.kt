package com.rdan.footballgather.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rdan.footballgather.FootballGatherApplication
import com.rdan.footballgather.ui.screens.details.PlayerDetailsViewModel
import com.rdan.footballgather.ui.screens.list.PlayerListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            PlayerListViewModel(
                playerRepository = footballGatherApplication().container.appRepository
            )
        }
        initializer {
            PlayerDetailsViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                playerRepository = footballGatherApplication().container.appRepository
            )
        }
    }
}

fun CreationExtras.footballGatherApplication(): FootballGatherApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FootballGatherApplication)