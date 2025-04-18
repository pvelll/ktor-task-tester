package com.sushkpavel.domain.model

import com.sushkpavel.utils.InstantSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class SolutionSubmission(
    val id: Int? = null,
    val userId: Int,
    val taskId: Long,
    val code: String,
    val language : String,
    @Serializable(with = InstantSerializer::class)
    val createdAt : Instant
)
