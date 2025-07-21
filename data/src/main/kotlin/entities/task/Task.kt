package com.sushkpavel.tasktester.entities.task

import kotlinx.serialization.Serializable
import com.sushkpavel.tasktester.utils.InstantSerializer
@Serializable
data class Task (
    val id : Long,
    val title : String,
    val description : String,
    val difficulty : Difficulty,
    val examples : String,
    @Serializable(with = InstantSerializer::class)
    val createdAt : java.time.Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt : java.time.Instant,
)