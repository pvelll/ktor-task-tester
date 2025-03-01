package com.sushkpavel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TestCaseDTO(
    val taskId : Int,
    val input : String,
    val expOutput : String,
)