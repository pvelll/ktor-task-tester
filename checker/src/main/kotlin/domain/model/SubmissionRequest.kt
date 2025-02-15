package com.sushkpavel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SubmissionRequest(
    val taskId: Int,
    val code: String,
    val language: String
)
