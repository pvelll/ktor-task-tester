package com.sushkpavel.domain.model

import com.sushkpavel.utils.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TestCase(
    val id: Int,
    val taskId: Long,
    val input: String,
    val expOutput: String,
    @Serializable(with = InstantSerializer::class) val createdAt: Instant
)