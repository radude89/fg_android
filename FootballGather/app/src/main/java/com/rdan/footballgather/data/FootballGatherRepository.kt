package com.rdan.footballgather.data

import com.rdan.footballgather.model.Gather
import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.Flow

interface FootballGatherRepository {
    fun getAllPlayers(): Flow<List<Player>>
    fun getPlayer(id: Long): Flow<Player?>
    suspend fun deletePlayer(player: Player)
    suspend fun insertPlayer(player: Player)
    suspend fun updatePlayer(player: Player)
    suspend fun insertGather(gather: Gather): Long
}