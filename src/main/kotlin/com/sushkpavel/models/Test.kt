package com.sushkpavel.models

import kotlinx.serialization.Serializable

@Serializable
data class Test(
    val id: String,
    val task: Task,
    val inputData: String,
    val expectedResult: String
)