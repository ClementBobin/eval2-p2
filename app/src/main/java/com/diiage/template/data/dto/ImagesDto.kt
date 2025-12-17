package com.diiage.template.data.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ImagesDto(
    @SerialName("jpg")
    val jpg: ImageUrlsDto
)


@Serializable
data class ImageUrlsDto(
    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("large_image_url")
    val largeImageUrl: String? = null
)