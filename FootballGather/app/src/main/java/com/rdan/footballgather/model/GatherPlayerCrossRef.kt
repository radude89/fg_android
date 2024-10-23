package com.rdan.footballgather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "gather_players",
    primaryKeys = ["gather_id", "player_id"],
    foreignKeys = [
        ForeignKey(
            entity = Gather::class,
            parentColumns = ["id"],
            childColumns = ["gather_id"]
        ),
        ForeignKey(
            entity = Player::class,
            parentColumns = ["id"],
            childColumns = ["player_id"]
        )
    ],
    indices = [
        Index(value = ["gather_id"]),
        Index(value = ["player_id"])
    ]
)
data class GatherPlayerCrossRef(
    @ColumnInfo(name = "gather_id")
    val gatherId: Long,

    @ColumnInfo(name = "player_id")
    val playerId: Long,

    @ColumnInfo(name = "team")
    val team: Team
)