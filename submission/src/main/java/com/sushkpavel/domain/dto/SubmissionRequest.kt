package com.sushkpavel.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubmissionRequest(
    val taskId: Int,
    val code: String,
    val language: String
)
