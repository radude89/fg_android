package com.rdan.footballgather.data

import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.Flow

class LocalFootballGatherRepository(
    private val footballGatherDao: FootballGatherDao
): FootballGatherRepository {
    override fun getAllPlayers(): Flow<List<Player>> =
        footballGatherDao.getAllPlayers()

    override fun getPlayer(id: Long): Flow<Player?> =
        footballGatherDao.getPlayer(id)

    override suspend fun deletePlayer(player: Player) =
        footballGatherDao.delete(player)

    override suspend fun insertPlayer(player: Player) =
        footballGatherDao.insert(player)
}