package com.diiage.template.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PaginationDto(
    @SerialName("has_next_page")
    val hasNextPage: Boolean
)