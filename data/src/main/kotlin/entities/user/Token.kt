package com.sushkpavel.tasktester.entities.user

import com.sushkpavel.tasktester.utils.InstantSerializer
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Token(
    val tokenId: Int,
    val userId: Int,
    val token: String,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    val expiresAt: Instant? = null
)
