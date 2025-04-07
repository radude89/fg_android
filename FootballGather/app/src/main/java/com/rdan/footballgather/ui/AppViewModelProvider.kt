package com.rdan.footballgather.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rdan.footballgather.FootballGatherApplication
import com.rdan.footballgather.ui.screens.add.PlayerAddViewModel
import com.rdan.footballgather.ui.screens.confirm.PlayersConfirmationViewModel
import com.rdan.footballgather.ui.screens.details.PlayerDetailsViewModel
import com.rdan.footballgather.ui.screens.edit.PlayerEditViewModel
import com.rdan.footballgather.ui.screens.gather.GatherViewModel
import com.rdan.footballgather.ui.screens.gather.timer.TimerControlViewModel
import com.rdan.footballgather.ui.screens.history.HistoryViewModel
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
        initializer {
            PlayerAddViewModel(
                playerRepository = footballGatherApplication().container.appRepository
            )
        }
        initializer {
            PlayerEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                playerRepository = footballGatherApplication().container.appRepository
            )
        }
        initializer {
            PlayersConfirmationViewModel(
                playerRepository = footballGatherApplication().container.appRepository
            )
        }
        initializer {
            GatherViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repository = footballGatherApplication().container.appRepository
            )
        }
        initializer {
            TimerControlViewModel()
        }
        initializer {
            HistoryViewModel(
                repository = footballGatherApplication().container.appRepository
            )
        }
    }
}

fun CreationExtras.footballGatherApplication(): FootballGatherApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FootballGatherApplication)