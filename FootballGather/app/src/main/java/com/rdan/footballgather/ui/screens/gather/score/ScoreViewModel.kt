package com.rdan.footballgather.ui.screens.gather.score

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScoreViewModel : ViewModel() {
    private val _teamAScore = MutableStateFlow(0)
    val teamAScore: StateFlow<Int> = _teamAScore.asStateFlow()

    private val _teamBScore = MutableStateFlow(0)
    val teamBScore: StateFlow<Int> = _teamBScore.asStateFlow()

    companion object {
        const val MAX_SCORE = 99
        const val MIN_SCORE = 0
    }

    fun incrementTeamA() {
        if(_teamAScore.value < MAX_SCORE) {
            _teamAScore.update { it + 1 }
        }
    }

    fun decrementTeamA() {
        if(_teamAScore.value > MIN_SCORE) {
            _teamAScore.update { it - 1 }
        }
    }

    fun incrementTeamB() {
        if(_teamBScore.value < MAX_SCORE) {
            _teamBScore.update { it + 1 }
        }
    }

    fun decrementTeamB() {
        if(_teamBScore.value > MIN_SCORE) {
            _teamBScore.update { it - 1 }
        }
    }
}