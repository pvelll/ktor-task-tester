package com.sushkpavel.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotifyMessageDTO(
    val message: String,
    val code: Int
)