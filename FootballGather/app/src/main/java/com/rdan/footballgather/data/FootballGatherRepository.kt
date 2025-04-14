package com.rdan.footballgather.data

import com.rdan.footballgather.model.Gather
import com.rdan.footballgather.model.GatherPlayerCrossRef
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.Flow

interface FootballGatherRepository {
    fun getAllPlayers(): Flow<List<Player>>
    fun getAllGathers(): Flow<List<Gather>>
    fun getPlayer(id: Long): Flow<Player?>
    suspend fun deletePlayer(player: Player)
    suspend fun insertPlayer(player: Player)
    suspend fun updatePlayer(player: Player)
    suspend fun insertGather(gather: Gather): Long
    suspend fun insertGatherPlayerCrossRef(pivot: GatherPlayerCrossRef)
    fun getPlayersForTeamInGather(team: Team, gather: Gather): Flow<List<Player>>
}