package com.rdan.footballgather.ui.screens.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import com.rdan.footballgather.model.Team.Companion.toDisplayName
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

    val hasPlayersInBothTeams: Boolean
        get () {
            val playerTeams = _uiState.value.playerTeams
            val hasPlayersInTeamA = playerTeams.any { it.value == Team.TeamA }
            val hasPlayersInTeamB = playerTeams.any { it.value == Team.TeamB }
            return hasPlayersInTeamA && hasPlayersInTeamB
        }

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

    fun getPlayerTeamsJson(): String {
        val playerTeams = _uiState.value.playerTeams
        val grouped = playerTeams.entries
            .filter { it.value != Team.Bench }
            .groupBy({ it.value }, { it.key.id })
            .mapKeys { toDisplayName(it.key) }
            .mapValues { it.value.joinToString(", ") }
        return Gson().toJson(grouped)
    }
}