package com.rdan.footballgather

import android.app.Application
import com.rdan.footballgather.data.AppContainer
import com.rdan.footballgather.data.AppDataContainer

class FootballGatherApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}