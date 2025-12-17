package com.diiage.template.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PaginationDto(
    @SerialName("has_next_page")
    val hasNextPage: Boolean,
)

@Serializable
data class AnimeListResponseDto(
    @SerialName("data")
    val data: List<AnimeDto>,

    @SerialName("pagination")
    val pagination: PaginationDto
)

@Serializable
data class AnimeDto(
    @SerialName("mal_id")
    val malId: Long,

    @SerialName("title")
    val title: String,

    @SerialName("title_english")
    val titleEnglish: String? = null,

    @SerialName("images")
    val images: ImagesDto,

    @SerialName("episodes")
    val episodes: Int? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("score")
    val score: Double? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("year")
    val year: Int? = null
)

@Serializable
data class ImagesDto(
    @SerialName("jpg")
    val jpg: ImageUrlsDto,
)

@Serializable
data class ImageUrlsDto(
    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("large_image_url")
    val largeImageUrl: String? = null
)