package com.rdan.footballgather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gathers")
data class Gather (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "completed_at")
    val completedAt: Long,

    @ColumnInfo(name = "score")
    val score: String,
)