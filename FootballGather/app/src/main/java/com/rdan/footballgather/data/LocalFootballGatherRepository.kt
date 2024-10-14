package com.rdan.footballgather.data

import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.PlayerPosition
import com.rdan.footballgather.model.PlayerSkill

class LocalFootballGatherRepository: FootballGatherRepository {
    override fun getAllPlayers(): List<Player> {
        return  listOf(
            Player(
                name = "John Doe",
                position = PlayerPosition.Goalkeeper,
                skill = PlayerSkill.Amateur
            ),
            Player(
                name = "Jane Doe",
                position = PlayerPosition.Midfielder,
                skill = PlayerSkill.Amateur
            ),
            Player(
                name = "Perry Smith",
                position = PlayerPosition.Forward,
                skill = PlayerSkill.Professional
            ),
            Player(
                name = "Larry Gompilski",
                position = PlayerPosition.Midfielder,
                skill = PlayerSkill.Amateur
            ),
        )
    }
}