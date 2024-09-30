package com.sushkpavel.models

import kotlinx.serialization.Serializable

@Serializable
data class TestResult(
    val id: String,
    val solution: Solution,
    val test: Test,
    val actualResult: String,
    val success: Boolean
)