package com.rdan.footballgather.ui.screens.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlayersConfirmationUiState(
    val playerList: List<Player> = listOf(),
    val teamNames: List<String> = Team.getTeamDisplayNames().toList(),
    val playerTeams: Map<Player, Team> = emptyMap()
)

class PlayersConfirmationViewModel(
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        PlayersConfirmationUiState()
    )

    val uiState: StateFlow<PlayersConfirmationUiState> =
        _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            playerRepository.getAllPlayers().collect { players ->
                _uiState.update { currentState ->
                    currentState.copy(playerList = players)
                }
            }
        }
    }

    fun updatePlayerTeam(player: Player, teamName: String) {
        val team = Team.fromDisplayName(teamName) ?: return
        _uiState.update { currentState ->
            currentState.copy(
                playerTeams = currentState.playerTeams
                    .toMutableMap()
                    .apply {
                        this[player] = team
                    }
            )
        }
    }

    fun getTeamName(player: Player): String? {
        val team = _uiState.value.playerTeams[player] ?: return null
        return Team.toDisplayName(team)
    }

    fun getTeamNames(): List<String> {
        return _uiState.value.teamNames
    }
}