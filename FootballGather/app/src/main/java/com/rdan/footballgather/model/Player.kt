package com.rdan.footballgather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "player_position")
    val position: PlayerPosition,

    @ColumnInfo(name = "player_skill")
    val skill: PlayerSkill
)