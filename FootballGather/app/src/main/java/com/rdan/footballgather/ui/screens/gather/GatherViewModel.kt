package com.rdan.footballgather.ui.screens.gather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Gather
import com.rdan.footballgather.model.GatherPlayerCrossRef
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import com.rdan.footballgather.ui.screens.gather.helpers.PlayerTeamsJsonMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant

data class GatherUiState(
    val teamAPlayers: List<Player> = emptyList(),
    val teamBPlayers: List<Player> = emptyList()
)

class GatherViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: FootballGatherRepository
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
            val mapper = PlayerTeamsJsonMapper(repository)
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

    suspend fun endGather(score: String) {
        val gatherId = saveGather(score)
        val uiStateValue = _uiState.value
        savePlayersToGather(
            gatherId = gatherId,
            players = uiStateValue.teamAPlayers,
            team = Team.TeamA
        )
        savePlayersToGather(
            gatherId = gatherId,
            players = uiStateValue.teamBPlayers,
            team = Team.TeamB
        )
    }

    private suspend fun saveGather(score: String): Long {
        val gather = Gather(
            id = 0L,
            completedAt = Instant.now().toEpochMilli(),
            score = score
        )
        return repository.insertGather(gather)
    }

    // TODO: Radu - insert data into player gather cross pivot table

    private suspend fun savePlayersToGather(
        gatherId: Long,
        players: List<Player>,
        team: Team
    ) {
        players.forEach { player ->
            val pivot = GatherPlayerCrossRef(
                playerId = player.id,
                gatherId = gatherId,
                team = Team.TeamA
            )
//            repository.insertGatherPlayerCrossRef(pivot)
        }
    }
}
