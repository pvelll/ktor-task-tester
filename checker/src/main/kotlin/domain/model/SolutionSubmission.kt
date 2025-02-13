package com.sushkpavel.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class SolutionSubmission(
    val id: Int? = null,
    val userId: Int,
    val taskId: Int,
    val code: String,
    val language : String,
    @Contextual val createdAt : Instant
)
