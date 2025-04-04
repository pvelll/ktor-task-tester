package com.sushkpavel.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val difficulty: String,
    val tags: List<String>
)
