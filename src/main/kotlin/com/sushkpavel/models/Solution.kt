package com.sushkpavel.models

import kotlinx.serialization.Serializable

@Serializable
data class Solution(
    val id: String,
    val user: User,
    val task: Task,
    val code: String,
    val status: String,
    val testResults: List<TestResult>
)