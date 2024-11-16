package com.sushkpavel.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class User(
    val userId: Int? = null,
    val username: String,
    val email: String,
    val passwordHash: String,
    @Contextual val createdAt: Instant? = null,
    @Contextual val updatedAt: Instant? = null
)
