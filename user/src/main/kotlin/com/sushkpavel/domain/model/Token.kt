package com.sushkpavel.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Token(
    val tokenId: Int,
    val userId: Int,
    val token: String,
    @Contextual val createdAt: Instant,
    @Contextual val expiresAt: Instant
)