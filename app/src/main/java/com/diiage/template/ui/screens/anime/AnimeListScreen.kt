package com.diiage.template.ui.screens.anime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.diiage.template.domain.model.Anime
import com.diiage.template.domain.model.SoundType
import com.diiage.template.ui.core.components.AnimeCard
import com.diiage.template.ui.core.components.ErrorDialog
import com.diiage.template.ui.core.components.Screen
import com.diiage.template.ui.core.components.input.SearchBar
import com.diiage.template.ui.core.components.layout.MainScaffold
import com.diiage.template.ui.core.components.state.EmptySearchResult
import com.diiage.template.ui.core.components.state.EmptyState
import com.diiage.template.ui.core.components.state.ErrorState
import com.diiage.template.ui.core.components.state.LoadingState

/**
 * Anime List and Search screen displaying top anime with search functionality.
 *
 * This screen implements the complete anime browsing experience with:
 * - Search bar for anime queries
 * - Infinite scrolling pagination
 * - Error handling with user feedback
 *
 * @param navController Navigation controller for screen transitions
 */
@Composable
fun AnimeScreen(navController: NavController) {
    Screen(
        viewModel = viewModel<AnimeViewModel>(),
        navController = navController
    ) { state, viewModel ->
        Content(
            navController = navController,
            state = state,
            handleAction = viewModel::handleAction
        )
    }
}

/**
 * Main content composable for the Anime screen.
 *
 * Displays loading, error, or anime list based on the current UI state.
 *
 * @param navController Navigation controller for screen transitions
 * @param state Current UI state of the screen
 * @param handleAction Function to handle UI actions
 */
@Composable
private fun Content(
    navController: NavController,
    state: AnimeContracts.UiState,
    handleAction: (AnimeContracts.UiAction) -> Unit
) {
    MainScaffold(navController = navController) { _ ->
        if (state.isLoading && state.animeList.isEmpty()) {
            LoadingState()
        } else if (state.error != null && state.animeList.isEmpty()) {
            ErrorState(
                error = state.error,
                onRetry = { handleAction(AnimeContracts.UiAction.LoadTopAnime) }
            )
        } else {
            AnimeContent(
                state = state,
                handleAction = handleAction
            )
        }
    }

    // Error Dialog
    if (state.showErrorDialog && state.error != null) {
        ErrorDialog(
            message = state.error,
            onDismiss = { handleAction(AnimeContracts.UiAction.DismissError) }
        )
    }
}

/**
 * Composable displaying the main anime content including search and list.
 *
 * @param state Current UI state of the screen
 * @param handleAction Function to handle UI actions
 */
@Composable
private fun AnimeContent(
    state: AnimeContracts.UiState,
    handleAction: (AnimeContracts.UiAction) -> Unit
) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                value = state.searchQuery,
                onValueChange = { handleAction(AnimeContracts.UiAction.SearchAnime(it) )                },
                placeholder = "Search anime...",
                modifier = Modifier.weight(1f)
            )

            // Refresh Button
            IconButton(
                onClick = {
                    handleAction(AnimeContracts.UiAction.PlaySound(SoundType.Click))
                    handleAction(AnimeContracts.UiAction.LoadTopAnime)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reload list",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Content Area
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                // Show empty search result state if no results found
                state.animeList.isEmpty() && state.searchQuery.text.isNotBlank() -> {
                    EmptySearchResult(
                        query = state.searchQuery.text,
                        onClearSearch = { handleAction(AnimeContracts.UiAction.ClearSearch) }
                    )
                }

                // Show empty state if no anime available
                state.animeList.isEmpty() -> {
                    EmptyState()
                }

                else -> {
                    AnimeList(
                        animeList = state.animeList,
                        listState = listState,
                        onAnimeClick = { handleAction(AnimeContracts.UiAction.PlaySound(SoundType.Click)) },
                        isLoadingMore = state.isLoading,
                        hasNextPage = state.hasNextPage
                    )
                }
            }

            // Show loading overlay when searching
            if (state.isSearching) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

/**
 * Composable displaying a list of anime with pagination support.
 *
 * @param animeList List of anime to display
 * @param listState State of the lazy list for scroll position
 * @param onAnimeClick Callback when an anime is clicked
 * @param isLoadingMore Indicates if more data is being loaded
 * @param hasNextPage Indicates if there are more pages to load
 */
@Composable
private fun AnimeList(
    animeList: List<Anime>,
    listState: LazyListState,
    onAnimeClick: (Anime) -> Unit,
    isLoadingMore: Boolean,
    hasNextPage: Boolean
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Anime items
        itemsIndexed(animeList) { index, anime ->
            AnimeCard(
                anime = anime,
                onClick = { onAnimeClick(anime) }
            )
        }
    }
}


/**
 * Composable to show a preview of the Anime screen.
 */
@Preview(showBackground = true)
@Composable
fun AnimeScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchBar(
                    value = TextFieldValue(""),
                    onValueChange = {},
                    placeholder = "Search anime..."
                )

                repeat(3) { index ->
                    AnimeCard(
                        anime = Anime(
                            id = index.toLong(),
                            title = "Preview Anime $index",
                            englishTitle = "Preview Anime $index",
                            imageUrl = null,
                            episodes = 12,
                            status = "Finished",
                            score = 8.5,
                            type = "TV",
                            year = 2023
                        ),
                        onClick = {}
                    )
                }
            }
        }
    }
}