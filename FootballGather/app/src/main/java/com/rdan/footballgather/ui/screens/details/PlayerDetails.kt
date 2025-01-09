package com.rdan.footballgather.ui.screens.details

import com.rdan.footballgather.model.Player

data class PlayerDetails(
    val id: Long = 0L,
    val name: String = "",
    val position: String = "",
    val skill: String = ""
)

fun Player.toPlayerDetails(): PlayerDetails = PlayerDetails(
    id = id,
    name = name,
    position = position.name,
    skill = skill.name,
)