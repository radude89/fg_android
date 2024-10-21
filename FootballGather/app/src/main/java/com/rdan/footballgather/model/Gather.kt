package com.rdan.footballgather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "gathers")
data class Gather (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "completed_at")
    val completedAt: Date,

    @ColumnInfo(name = "score")
    val score: String,

    val teamAPlayers: List<Player>,
    val teamBPlayers: List<Player>
)