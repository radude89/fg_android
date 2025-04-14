package com.rdan.footballgather.data

import com.rdan.footballgather.model.Gather
import com.rdan.footballgather.model.GatherPlayerCrossRef
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.Flow

class LocalFootballGatherRepository(
    private val footballGatherDao: FootballGatherDao
): FootballGatherRepository {
    override fun getAllPlayers(): Flow<List<Player>> =
        footballGatherDao.getAllPlayers()

    override fun getAllGathers(): Flow<List<Gather>> =
        footballGatherDao.getAllGathers()

    override fun getPlayer(id: Long): Flow<Player?> =
        footballGatherDao.getPlayer(id)

    override suspend fun deletePlayer(player: Player) =
        footballGatherDao.delete(player)

    override suspend fun insertPlayer(player: Player) =
        footballGatherDao.insert(player)

    override suspend fun updatePlayer(player: Player) =
        footballGatherDao.update(player)

    override suspend fun insertGather(gather: Gather): Long {
        return footballGatherDao.insert(gather)
    }

    override suspend fun insertGatherPlayerCrossRef(pivot: GatherPlayerCrossRef) {
        footballGatherDao.insertGatherPlayerCrossRef(pivot)
    }

    override fun getPlayersForTeamInGather(
        team: Team,
        gather: Gather
    ): Flow<List<Player>> =
        footballGatherDao.getPlayersForTeamInGather(
            gatherId = gather.id,
            team = team
        )

    override suspend fun deleteGather(gather: Gather) =
        footballGatherDao.delete(gather)

    override suspend fun deleteGatherPlayers(gatherId: Long) =
        footballGatherDao.deleteGatherPlayers(gatherId)
}