package com.example.seekhoanime.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoanime.AnimeUiEvents
import com.example.seekhoanime.R
import com.example.seekhoanime.domain.model.TopRatedAnimeInfo
import com.example.seekhoanime.domain.repository.AnimeRepository
import com.example.seekhoanime.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository, val context: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeUiEvents<List<TopRatedAnimeInfo>>>(AnimeUiEvents.Loading)
    val uiState: StateFlow<AnimeUiEvents<List<TopRatedAnimeInfo>>> = _uiState.asStateFlow()

    // Fetch from API, insert into DB and emit result
    fun getAnimeFromApiAndSave() {
        viewModelScope.launch(Dispatchers.IO)  {
            _uiState.value = AnimeUiEvents.Loading

            if (!Utility.isNetworkAvailable(context)) {
                _uiState.value =
                    AnimeUiEvents.Error(context.getString(R.string.no_internet_connection))
                fetchAnimeListFromLocal()
                return@launch
            }

            try {
                repository.fetchAnimeData().collect { animeTable ->
                    val animeList = animeTable.data
                    repository.insertAnimeToDb(animeList)
                    _uiState.value = AnimeUiEvents.Success(animeList)
                }
            } catch (e: Exception) {
                _uiState.value = AnimeUiEvents.Error(
                    e.localizedMessage ?: context.getString(R.string.something_went_wrong)
                )
            }
        }
    }

    // Fetch from local DB
    fun fetchAnimeListFromLocal() {
        viewModelScope.launch(Dispatchers.IO)  {
            repository.getAnimeFromDb().collect {
                _uiState.value = AnimeUiEvents.Success(it)
            }
        }
    }
}
