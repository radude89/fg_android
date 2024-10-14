package com.rdan.footballgather.data

import com.rdan.footballgather.model.Player

interface FootballGatherRepository {
    fun getAllPlayers(): List<Player>
}