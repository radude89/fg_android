package com.rdan.footballgather.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Gather
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class HistoryUiState(val gathers: List<Gather> = listOf())

class HistoryViewModel(
    repository: FootballGatherRepository
) : ViewModel() {

    val uiState: StateFlow<HistoryUiState> =
        repository.getAllGathers()
            .map { HistoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HistoryUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}