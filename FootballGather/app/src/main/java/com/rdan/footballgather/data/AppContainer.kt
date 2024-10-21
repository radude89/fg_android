package com.rdan.footballgather.data

import android.content.Context

interface AppContainer {
    val appRepository: FootballGatherRepository
}

class AppDataContainer(
    private val context: Context
) : AppContainer {
    override val appRepository: FootballGatherRepository by lazy {
        LocalFootballGatherRepository()
    }
}