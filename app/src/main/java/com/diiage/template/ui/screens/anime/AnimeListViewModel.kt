package com.diiage.template.ui.screens.anime

import android.app.Application
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.diiage.template.domain.repository.*
import com.diiage.template.domain.model.Anime
import com.diiage.template.domain.model.SoundType
import com.diiage.template.domain.model.VibrationType
import com.diiage.template.ui.core.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.inject

/** Contracts for the Anime screen, including UI state and actions. */
interface AnimeContracts {
    /** Represents UI state for the anime screen. */
    data class UiState(
        val isLoading: Boolean = false,
        val isSearching: Boolean = false,
        val animeList: List<Anime> = emptyList(),
        val searchQuery: TextFieldValue = TextFieldValue(""),
        val error: String? = null,
        val showErrorDialog: Boolean = false,
        val page: Int = 1,
        val hasNextPage: Boolean = true,
        val selectedAnime: Anime? = null
    )

    /** Represents various UI actions that can be performed. */
    sealed interface UiAction {
        object LoadTopAnime : UiAction
        data class SearchAnime(val query: TextFieldValue) : UiAction
        data class PlaySound(val soundType: SoundType) : UiAction
        object DismissError : UiAction
        object ClearSearch : UiAction
    }
}

/** ViewModel for managing the state and actions related to anime data.
 *
 * @param application The application context.
 */
class AnimeViewModel(
    application: Application
) : ViewModel<AnimeContracts.UiState>(
    initialState = AnimeContracts.UiState(),
    application = application
) {
    private val animeRepository: AnimeRepository by inject()
    private val soundManager: SoundManagerRepository by inject()
    private val vibrationManager : VibrationManagerRepository by inject()
    private val _uiState = MutableStateFlow(AnimeContracts.UiState())
    val uiState: StateFlow<AnimeContracts.UiState> = _uiState.asStateFlow()

    // Initial load of top anime when ViewModel is created.
    init {
        loadTopAnime()
    }

    /** Handles the incoming UI actions. */
    fun handleAction(action: AnimeContracts.UiAction) {
        when (action) {
            is AnimeContracts.UiAction.LoadTopAnime -> loadTopAnime()
            is AnimeContracts.UiAction.SearchAnime -> searchAnime(action.query)
            is AnimeContracts.UiAction.PlaySound -> playSoundAsync(action.soundType)
            AnimeContracts.UiAction.DismissError -> dismissError()
            AnimeContracts.UiAction.ClearSearch -> clearSearch()
        }
    }

    /** Loads the top anime list from the repository. */
    private fun loadTopAnime() {
        updateState { copy(isLoading = true, error = null) }

        fetchData(
            source = { animeRepository.getTopAnime(uiState.value.page) }
        ) {
            onSuccess { animeList ->
                updateState {
                    copy(
                        isLoading = false,
                        animeList = animeList,
                        hasNextPage = animeList.isNotEmpty(),
                        error = null
                    )
                }

                playSoundAsync(SoundType.Success, VibrationType.Success)
            }
            onFailure { error ->
                updateState {
                    copy(
                        isLoading = false,
                        error = "Failed to load anime: ${error.message}",
                        showErrorDialog = true
                    )
                }

                playSoundAsync(SoundType.Error, VibrationType.Error)
            }
        }
    }

    /** Searches for anime based on the provided query.
     *
     *
     * @param query The search query as a TextFieldValue.
     */
    private fun searchAnime(query: TextFieldValue) {
        updateState { copy(isSearching = true, searchQuery = query, error = null) }

        fetchData(
            source = { animeRepository.searchAnime(query.text, uiState.value.page) }
        ) {
            onSuccess { animeList ->
                updateState {
                    copy(
                        isSearching = false,
                        animeList = animeList,
                        hasNextPage = animeList.isNotEmpty(),
                        error = null
                    )
                }

                playSoundAsync(SoundType.Success, VibrationType.Success)
            }
            onFailure { error ->
                updateState {
                    copy(
                        isSearching = false,
                        error = "Search failed: ${error.message}",
                        showErrorDialog = true
                    )
                }

                playSoundAsync(SoundType.Error, VibrationType.Error)
            }
        }
    }


    /**
     * Plays sound asynchronously without blocking the UI.
     *
     * @param soundType The type of sound to play.
     */
    private fun playSoundAsync(soundType: SoundType, vibrationType: VibrationType = VibrationType.Click) {
        viewModelScope.launch {
            soundManager.playSound(soundType)
            vibrationManager.vibrate(vibrationType)
        }
    }

    /** Dismisses the error dialog and clears the error message. */
    private fun dismissError() {
        updateState { copy(showErrorDialog = false, error = null) }
    }

    /** Clears the search query and reloads the top anime list. */
    private fun clearSearch() {
        updateState { copy(searchQuery = TextFieldValue(""), animeList = emptyList()) }
        loadTopAnime()
    }
}