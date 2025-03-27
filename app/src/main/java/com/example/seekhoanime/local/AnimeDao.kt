package com.example.seekhoanime.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.seekhoanime.domain.model.TopRatedAnimeInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_table")
    fun getAllAnimeInfo(): Flow<List<TopRatedAnimeInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnimeInfo(animeList: List<TopRatedAnimeInfo>)
}