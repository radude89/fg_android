package com.rdan.footballgather.model

import java.util.Date

data class Gather (
    val completedAt: Date,
    val score: String,
    val teamAPlayers: List<Player>,
    val teamBPlayers: List<Player>
)