package com.sushkpavel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TestCaseResult(
    val testId: String,
    val success: Boolean,
    val actualResult: String,
    val expectedResult: String? = null,
    val error: String? = null
)
