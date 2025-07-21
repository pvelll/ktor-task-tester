package com.sushkpavel.tasktester.entities.checker

import kotlinx.serialization.Serializable
import com.sushkpavel.tasktester.utils.InstantSerializer
import java.time.Instant

@Serializable
data class TestCase(
    val id: Int,
    val taskId: Long,
    val input: String,
    val expOutput: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant
)