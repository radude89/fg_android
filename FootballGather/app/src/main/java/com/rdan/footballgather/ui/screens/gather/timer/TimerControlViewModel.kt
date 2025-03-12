package com.rdan.footballgather.ui.screens.gather.timer

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    companion object {
        const val INITIAL_MIN = 10
        const val INITIAL_SEC = 0
    }

    var uiState by mutableStateOf(TimerControlUiState())
        private set

    private var initialMin = INITIAL_MIN
    private var initialSec = INITIAL_SEC

    var remainingMin by mutableIntStateOf(initialMin)
        private set
    var remainingSec by mutableIntStateOf(initialSec)
        private set
    val formattedRemainingMin: String
        get() { return formatTimeUnit(remainingMin) }
    val formattedRemainingSec: String
        get() { return formatTimeUnit(remainingSec) }

    var isRunning by mutableStateOf(false)
        private set

    fun setTime(min: Int, sec: Int) {
        initialMin = min
        initialSec = sec
        remainingMin = min
        remainingSec = sec
        onCancelClicked()
    }

    fun onCancelClicked() {
        uiState = uiState.copy(
            timerState = TimerState.Idle,
            startButtonText = R.string.start,
            isSetTimeEnabled = true,
            isCancelEnabled = false
        )
    }

    fun onStartOrPauseClicked() {
        when (uiState.timerState) {
            TimerState.Idle, TimerState.Paused -> onStartOrResumeClicked()
            TimerState.Running -> onPauseClicked()
        }
    }

    private fun formatTimeUnit(timeUnit: Int): String {
        return timeUnit.toString().padStart(2, '0')
    }

    private fun onStartOrResumeClicked() {
        uiState = uiState.copy(
            timerState = TimerState.Running,
            startButtonText = R.string.pause,
            isSetTimeEnabled = false,
            isCancelEnabled = true
        )
    }

    private fun onPauseClicked() {
        uiState = uiState.copy(
            timerState = TimerState.Paused,
            startButtonText = R.string.resume,
            isSetTimeEnabled = true,
            isCancelEnabled = true
        )
    }
}