package com.example.seekhoanime.ui.animedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoanime.domain.model.AnimeDetailsModel
import com.example.seekhoanime.AnimeUiEvents
import com.example.seekhoanime.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AnimeUiEvents<AnimeDetailsModel>>(AnimeUiEvents.Loading)
    val uiState: StateFlow<AnimeUiEvents<AnimeDetailsModel>> = _uiState.asStateFlow()

    fun getAnimeDetail(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = AnimeUiEvents.Loading
            repository.getAnimeDetails(animeId).catch { e ->
                _uiState.value = AnimeUiEvents.Error(e.message ?: "Something went wrong")
            }.collect { animeTable ->
                _uiState.value = AnimeUiEvents.Success(animeTable)
            }
        }
    }
}
