package com.sushkpavel.infrastructure.dto

import com.sushkpavel.com.sushkpavel.domain.model.Difficulty
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO (
    val id : Long? = null,
    val title : String,
    val description : String,
    val difficulty : Difficulty,
    val examples : String,
)