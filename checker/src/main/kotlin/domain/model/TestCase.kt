package com.sushkpavel.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TestCase(
    val id : Int,
    val taskId : Int,
    val input : String,
    val expOutput : String,
    @Contextual val createdAt : Instant
)