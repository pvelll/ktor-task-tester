package com.sushkpavel.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
    val id: String,
    val userId: String,
    val solutionId: String,
    val testId: String,
    val actualResult: String,
    val success: Boolean
)