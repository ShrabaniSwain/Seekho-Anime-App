package com.example.seekhoanime

sealed class AnimeUiEvents<out T> {
    data class Success<out T>(val data: T) : AnimeUiEvents<T>()
    data class Error(val message: String) : AnimeUiEvents<Nothing>()
    object Loading : AnimeUiEvents<Nothing>()
}
