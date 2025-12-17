package com.diiage.template.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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