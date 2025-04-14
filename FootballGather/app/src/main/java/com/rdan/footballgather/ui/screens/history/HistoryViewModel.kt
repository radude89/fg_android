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
            val gathers = repository.getAllGathers().first()
            val gatherPlayersMap: Map<Gather, Map<Team, List<Player>>> =
                gathers.associateWith { gather ->
                    mapOf(
                        Team.TeamA to repository.getPlayersForTeamInGather(
                            Team.TeamA, gather
                        ).first(),

                        Team.TeamB to repository.getPlayersForTeamInGather(
                            Team.TeamB, gather
                        ).first()
                    )
            }

            _uiState.value = HistoryUiState(
                gathers = gathers,
                gatherPlayersMap = gatherPlayersMap
            )
        }
    }

    fun getPlayerLine(team: Team, gather: Gather): String {
        val players = _uiState.value.gatherPlayersMap[gather]?.get(team)
        return players?.joinToString(", ") { it.name } ?: ""
    }
}