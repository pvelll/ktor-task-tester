package com.sushkpavel.domain.model

import com.sushkpavel.utils.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TestResult(
    val id: Int,
    val userId: Int,
    val submissionId: Int,
    val taskId: Long,
    val actualResult: String,
    val success: Boolean,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant
)