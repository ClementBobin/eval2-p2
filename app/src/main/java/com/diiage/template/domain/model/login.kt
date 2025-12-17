package com.diiage.template.domain.model

data class LoginResponse(
    val success: Boolean,
    val userId: String? = null,
    val error: String? = null
)