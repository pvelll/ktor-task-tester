package com.sushkpavel.infrastructure.dto

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TestResultDTO(
    val testId: Int,
    val actualResult: String,
    val success: Boolean
)