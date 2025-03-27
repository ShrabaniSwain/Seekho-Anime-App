package com.example.seekhoanime.service

import com.example.seekhoanime.domain.model.AnimeDetailsModel
import com.example.seekhoanime.domain.model.AnimeTable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): AnimeTable

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetail(@Path("anime_id") id: Int): AnimeDetailsModel
}