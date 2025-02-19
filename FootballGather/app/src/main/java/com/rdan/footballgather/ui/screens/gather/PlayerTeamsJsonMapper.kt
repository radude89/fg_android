package com.rdan.footballgather.ui.screens.gather

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.firstOrNull

class PlayerTeamsJsonMapper(
    private val playerRepository: FootballGatherRepository
) {
    suspend fun createMap(json: String): Map<Player, Team> {
        val typeToken = TypeToken.getParameterized(
            Map::class.java,
            String::class.java,
            String::class.java
        ).type
        val teamPlayerIdsMap: Map<String, String> = Gson().fromJson(json, typeToken)
        return buildPlayerTeamsMap(teamPlayerIdsMap)
    }

    private suspend fun buildPlayerTeamsMap(
        teamPlayerIdsMap: Map<String, String>
    ): Map<Player, Team> {
        val playerTeams: MutableMap<Player, Team> = mutableMapOf()
        teamPlayerIdsMap.forEach { (teamName, playerIdsString) ->
            processTeamEntry(teamName, playerIdsString, playerTeams)
        }
        return playerTeams
    }

    private suspend fun processTeamEntry(
        teamName: String,
        playerIdsString: String,
        playerTeams: MutableMap<Player, Team>
    ) {
        val team = Team.fromDisplayName(teamName) ?: return
        val playerIds = extractPlayerIds(playerIdsString)
        addPlayersToTeam(playerIds, team, playerTeams)
    }

    private fun extractPlayerIds(playerIdsString: String): List<Long> {
        return playerIdsString
            .split(",")
            .mapNotNull { it.trim().toLongOrNull() }
    }

    private suspend fun addPlayersToTeam(
        playerIds: List<Long>,
        team: Team,
        playerTeams: MutableMap<Player, Team>
    ) {
        playerIds.forEach { playerId ->
            val player = playerRepository.getPlayer(playerId).firstOrNull()
            player?.let { playerTeams[it] = team }
        }
    }
}