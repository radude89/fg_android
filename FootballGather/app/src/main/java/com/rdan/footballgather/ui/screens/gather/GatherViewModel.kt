package com.rdan.footballgather.ui.screens.gather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GatherUiState(
    val teamAPlayers: List<Player> = emptyList(),
    val teamBPlayers: List<Player> = emptyList()
)

class GatherViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    private val playerTeamsJson: String = checkNotNull(
        savedStateHandle[GatherDestination.PLAYER_TEAMS_ARG]
    )

    private val _uiState = MutableStateFlow(
        GatherUiState()
    )

    val uiState: StateFlow<GatherUiState> =
        _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val mapper = PlayerTeamsJsonMapper(playerRepository)
            val playerTeams = mapper.createMap(playerTeamsJson)
            _uiState.value = GatherUiState(
                teamAPlayers = filterPlayers(Team.TeamA, playerTeams),
                teamBPlayers = filterPlayers(Team.TeamB, playerTeams)
            )
        }
    }

    private fun filterPlayers(
        team: Team,
        playerTeams: Map<Player, Team>
    ): List<Player> {
        return playerTeams.filterValues { it == team }.keys.toList()
    }
}
