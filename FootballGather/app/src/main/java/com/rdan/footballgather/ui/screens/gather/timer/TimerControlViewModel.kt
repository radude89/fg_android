package com.rdan.footballgather.ui.screens.gather.timer

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdan.footballgather.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        const val SECONDS_UPPER_BOUND = 59
    }
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

    private fun formatTimeUnit(timeUnit: Int): String {
        return timeUnit.toString().padStart(2, '0')
    }

    var uiState by mutableStateOf(TimerControlUiState())
        private set

    var isRunning by mutableStateOf(false)
        private set

    fun startCountdown() {
        viewModelScope.launch {
            while (isRunning && (remainingMin > 0 || remainingSec > 0)) {
                handleTimerTick()
                pauseForOneSecond()
            }
            stopTimerIfNeeded()
        }
    }

    private fun handleTimerTick() {
        if (!decrementSecondsIfPossible()) {
            decrementMinutesIfPossible()
        }
    }

    private suspend fun pauseForOneSecond() {
        delay(1000L)
    }

    private fun decrementSecondsIfPossible(): Boolean {
        if (remainingSec > 0) {
            remainingSec--
            return true
        }
        return false
    }

    private fun decrementMinutesIfPossible(): Boolean {
        if (remainingMin > 0) {
            remainingMin--
            remainingSec = SECONDS_UPPER_BOUND
            return true
        }
        return false
    }

    private fun stopTimerIfNeeded() {
        if(timerHasReachedZero() && isRunning) {
            onCancel()
        }
    }

    private fun timerHasReachedZero(): Boolean {
        return remainingMin == 0 && remainingSec == 0
    }

    fun setTime(min: Int, sec: Int) {
        initialMin = min
        initialSec = sec
        remainingMin = min
        remainingSec = sec
    }

    fun onCancel() {
        setTime(initialMin, initialSec)
        isRunning = false
        uiState = uiState.copy(
            timerState = TimerState.Idle,
            startButtonText = R.string.start,
            isSetTimeEnabled = true,
            isCancelEnabled = false
        )
    }

    fun onStartOrPause() {
        when (uiState.timerState) {
            TimerState.Idle, TimerState.Paused -> onStartOrResume()
            TimerState.Running -> onPause()
        }
    }

    private fun onStartOrResume() {
        isRunning = true
        uiState = uiState.copy(
            timerState = TimerState.Running,
            startButtonText = R.string.pause,
            isSetTimeEnabled = false,
            isCancelEnabled = true
        )
    }

    private fun onPause() {
        isRunning = false
        uiState = uiState.copy(
            timerState = TimerState.Paused,
            startButtonText = R.string.resume,
            isSetTimeEnabled = true,
            isCancelEnabled = true
        )
    }
}