package com.example.seekhoanime.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seekhoanime.domain.model.TopRatedAnimeInfo

@Database(entities = [TopRatedAnimeInfo::class], version = 1, exportSchema = false)
@TypeConverters(AnimeImageConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}