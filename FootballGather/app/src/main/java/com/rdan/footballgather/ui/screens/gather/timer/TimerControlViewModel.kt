package com.rdan.footballgather.ui.screens.gather.timer

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.rdan.footballgather.R

sealed class TimerState {
    data object Idle : TimerState()
    data object Running : TimerState()
    data object Paused : TimerState()
}

data class TimerControlUiState(
    val timerState: TimerState = TimerState.Idle,
    @StringRes val startButtonText: Int = R.string.start,
    val isSetTimeEnabled: Boolean = true,
    val isCancelEnabled: Boolean = false
)

class TimerControlViewModel : ViewModel() {
    private var _uiState by mutableStateOf(TimerControlUiState())
    val uiState: TimerControlUiState
        get() = _uiState

    fun onCancelClicked() {
        _uiState = _uiState.copy(
            timerState = TimerState.Idle,
            startButtonText = R.string.start,
            isSetTimeEnabled = true,
            isCancelEnabled = false
        )
    }

    fun onStartOrPauseClicked() {
        when (_uiState.timerState) {
            TimerState.Idle, TimerState.Paused -> onStartOrResumeClicked()
            TimerState.Running -> onPauseClicked()
        }
    }

    private fun onStartOrResumeClicked() {
        _uiState = _uiState.copy(
            timerState = TimerState.Running,
            startButtonText = R.string.pause,
            isSetTimeEnabled = false,
            isCancelEnabled = true
        )
    }

    private fun onPauseClicked() {
        _uiState = _uiState.copy(
            timerState = TimerState.Paused,
            startButtonText = R.string.resume,
            isSetTimeEnabled = true,
            isCancelEnabled = true
        )
    }
}