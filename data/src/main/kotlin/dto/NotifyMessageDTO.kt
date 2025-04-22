package com.sushkpavel.tasktester.dto

import kotlinx.serialization.Serializable

@Serializable
data class NotifyMessageDTO(
    val message: String,
    val code: Int
)