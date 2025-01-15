package com.rdan.footballgather.ui.screens.details

import com.rdan.footballgather.model.Player
import com.rdan.footballgather.model.PlayerPosition
import com.rdan.footballgather.model.PlayerSkill
import java.time.Instant

data class PlayerDetails(
    val id: Long = 0L,
    val name: String = "",
    val position: String? = null,
    val skill: String? = null
)

fun Player.toPlayerDetails(): PlayerDetails = PlayerDetails(
    id = id,
    name = name,
    position = position?.name,
    skill = skill?.name,
)

fun PlayerDetails.toPlayer(): Player = Player(
    id = id,
    createdAt = Instant.now().toEpochMilli(),
    name = name,
    position = position?.let { PlayerPosition.valueOf(it) },
    skill = skill?.let { PlayerSkill.valueOf(it) },
)