package com.rdan.footballgather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface FootballGatherDao {
    @Query("SELECT * FROM players ORDER BY created_at DESC")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayer(id: Long): Flow<Player>

    @Delete
    suspend fun delete(player: Player)

    @Transaction
    @Query("""
        SELECT p.* FROM players p
        INNER JOIN gather_players gpc ON p.id = gpc.player_id
        WHERE gpc.gather_id = :gatherId AND gpc.team = :team
    """)
    fun getPlayersForTeamInGather(
        gatherId: Long,
        team: Team
    ): Flow<List<Player>>
}