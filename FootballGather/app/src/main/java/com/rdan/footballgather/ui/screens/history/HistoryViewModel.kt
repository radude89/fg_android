package com.rdan.footballgather.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Gather
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class HistoryUiState(
    val gathers: List<Gather> = listOf(),
    val gatherPlayersMap: Map<Gather, Map<Team, List<Player>>> = mapOf()
)

class HistoryViewModel(
    private val repository: FootballGatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())

    val uiState: StateFlow<HistoryUiState> =
        _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            updateUiState()
        }
    }

    fun getPlayerLine(team: Team, gather: Gather): String {
        val players = _uiState.value.gatherPlayersMap[gather]?.get(team)
        return players?.joinToString(", ") { it.name } ?: ""
    }

    fun deleteGather(gather: Gather) {
        viewModelScope.launch {
            repository.deleteGatherPlayers(gatherId = gather.id)
            repository.deleteGather(gather)
            updateUiState()
        }
    }

    private suspend fun updateUiState() {
        val updatedGathers = repository.getAllGathers().first()
        val updatedGatherPlayersMap = updatedGathers.associateWith { g ->
            mapOf(
                Team.TeamA to repository.getPlayersForTeamInGather(Team.TeamA, g).first(),
                Team.TeamB to repository.getPlayersForTeamInGather(Team.TeamB, g).first()
            )
        }
        _uiState.value = HistoryUiState(
            gathers = updatedGathers,
            gatherPlayersMap = updatedGatherPlayersMap
        )
    }
}