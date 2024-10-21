package com.rdan.footballgather.data

import android.content.Context

interface AppContainer {
    val appRepository: FootballGatherRepository
    val appDatabase: FootballGatherDatabase
}

class AppDataContainer(
    private val context: Context
) : AppContainer {
    override val appRepository: FootballGatherRepository by lazy {
        LocalFootballGatherRepository(appDatabase.footballGatherDao())
    }

    override val appDatabase: FootballGatherDatabase by lazy {
        FootballGatherDatabase.getDatabase(context)
    }
}