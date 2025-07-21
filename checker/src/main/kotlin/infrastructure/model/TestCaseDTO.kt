package com.sushkpavel.infrastructure.model

import kotlinx.serialization.Serializable

@Serializable
data class TestCaseDTO(
    val taskId : Long,
    val input : String,
    val expOutput : String,
)