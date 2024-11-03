package com.sushkpavel.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CodeTest(
    val id: String,
    val taskId: String,
    val inputData: List<String>,
    val expectedResult: List<String>
)