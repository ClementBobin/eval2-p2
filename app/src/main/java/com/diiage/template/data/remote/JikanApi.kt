package com.diiage.template.data.remote

import com.diiage.template.data.dto.AnimeListResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Service class to interact with the Jikan API for fetching anime data.
 *
 * @property client The HTTP client used to make requests to the Jikan API.
 */
internal class JikanApi(private val client: HttpClient) {
    /**
     * Fetches the top anime list from the Jikan API.
     *
     * @param page The page number for pagination (default: 1)
     * @return [AnimeListResponseDto] containing the list of top anime
     */
    suspend fun getTopAnime(page: Int = 1): AnimeListResponseDto {
        return client.get("top/anime") {
            parameter("page", page)
            parameter("limit", 25)
        }.body()
    }

    /**
     * Searches for anime by title query.
     *
     * @param query The search query string
     * @param page The page number for pagination (default: 1)
     * @return [AnimeListResponseDto] containing the search results
     */
    suspend fun searchAnime(query: String, page: Int = 1): AnimeListResponseDto {
        return client.get("anime") {
            parameter("q", query)
            parameter("page", page)
            parameter("limit", 25)
        }.body()
    }
}