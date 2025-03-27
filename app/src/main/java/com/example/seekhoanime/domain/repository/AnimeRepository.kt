package com.example.seekhoanime.domain.repository


import com.example.seekhoanime.local.AnimeDao
import com.example.seekhoanime.domain.model.AnimeDetailsModel
import com.example.seekhoanime.domain.model.AnimeTable
import com.example.seekhoanime.service.ApiService
import com.example.seekhoanime.domain.model.TopRatedAnimeInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: ApiService,
    private val animeDao: AnimeDao
) {
    // Fetch anime from API as Flow
    fun fetchAnimeData(): Flow<AnimeTable> = flow {
        val response = apiService.getTopAnime()
        emit(response)
    }

    // Get anime from local DB as Flow
    fun getAnimeFromDb(): Flow<List<TopRatedAnimeInfo>> = animeDao.getAllAnimeInfo()

    // Insert anime list into Room DB
    suspend fun insertAnimeToDb(animeList: List<TopRatedAnimeInfo>) {
        animeDao.insertAllAnimeInfo(animeList)
    }

    fun getAnimeDetails(id: Int): Flow<AnimeDetailsModel> = flow {
        val response = apiService.getAnimeDetail(id)
        emit(response)
    }

}