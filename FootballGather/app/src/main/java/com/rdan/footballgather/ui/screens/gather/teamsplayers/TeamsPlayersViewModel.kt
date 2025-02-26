package com.rdan.footballgather.ui.screens.gather.teamsplayers

import androidx.lifecycle.ViewModel
import com.rdan.footballgather.model.Player

class TeamsPlayersViewModel(
    val teamAPlayers: List<Player>,
    val teamBPlayers: List<Player>
) : ViewModel()