package com.rdan.footballgather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rdan.footballgather.converters.DateConverter
import com.rdan.footballgather.model.Player

@Database(entities = [Player::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class FootballGatherDatabase : RoomDatabase() {
    abstract fun footballGatherDao(): FootballGatherDao

    companion object {
        private var Instance: FootballGatherDatabase? = null

        fun getDatabase(context: Context): FootballGatherDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FootballGatherDatabase::class.java,
                    "football_gather_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}