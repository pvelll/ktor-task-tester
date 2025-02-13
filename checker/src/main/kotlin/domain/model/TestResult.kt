package com.sushkpavel.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TestResult(
    val id: String,
    val userId: String,
    val solutionId: String,
    val testId: String,
    val actualResult: String,
    val success: Boolean,
    @Contextual val createdAt : Instant
)