package com.sushkpavel.models

import kotlinx.serialization.Serializable

@Serializable
data class SolutionSubmission(
    val id: String,
    val userId: String,
    val taskId: String,
    val code: String,
    val language : String,
    val input: String,
    val status: String
)
