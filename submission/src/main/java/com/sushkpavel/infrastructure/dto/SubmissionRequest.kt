package com.sushkpavel.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubmissionRequest(
    val taskId: Long,
    val code: String,
    val language: String
)
