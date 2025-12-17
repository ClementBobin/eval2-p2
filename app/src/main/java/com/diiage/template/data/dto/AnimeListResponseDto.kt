package com.diiage.template.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class AnimeListResponseDto(
    @SerialName("data")
    val data: List<AnimeDto>,

    @SerialName("pagination")
    val pagination: PaginationDto
)