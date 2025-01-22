package com.rdan.footballgather.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.data.FootballGatherRepository
import com.rdan.footballgather.ui.components.forms.PlayerDetails
import com.rdan.footballgather.ui.components.forms.toPlayer
import com.rdan.footballgather.ui.components.forms.toPlayerDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class PlayerDetailsUiState(
    val playerDetails: PlayerDetails = PlayerDetails()
)

class PlayerDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val playerRepository: FootballGatherRepository
) : ViewModel() {
    private val playerId: Long = checkNotNull(
        savedStateHandle[PlayerDetailsDestination.PLAYER_ID_ARG]
    )

    val uiState: StateFlow<PlayerDetailsUiState> =
        playerRepository.getPlayer(playerId)
            .filterNotNull()
            .map {
                PlayerDetailsUiState(
                    playerDetails = it.toPlayerDetails()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PlayerDetailsUiState()
            )

    fun formattedPlayerCreationDate(createdAt: Long): String {
        val localDateTime = LocalDateTime
            .ofInstant(
                Date(createdAt).toInstant(),
                ZoneId.systemDefault()
            )
        return DateTimeFormatter
            .ofPattern("MMM dd, yyyy", Locale.getDefault())
            .format(localDateTime)
    }

    suspend fun deletePlayer() {
        playerRepository.deletePlayer(
            uiState.value.playerDetails.toPlayer()
        )
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}