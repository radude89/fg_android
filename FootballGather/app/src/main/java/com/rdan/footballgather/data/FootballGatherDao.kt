package com.rdan.footballgather.data

import androidx.room.Dao
import androidx.room.Query
import com.rdan.footballgather.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface FootballGatherDao {
    @Query("SELECT * FROM players ORDER BY created_at DESC")
    fun getAllPlayers(): Flow<List<Player>>
}