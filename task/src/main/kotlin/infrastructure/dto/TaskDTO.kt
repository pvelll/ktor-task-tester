package com.sushkpavel.infrastructure.dto

import com.sushkpavel.domain.model.Task
import com.sushkpavel.tasktester.Difficulty
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TaskDTO(
    val id: Long? = null,
    val title: String,
    val description: String,
    val difficulty: Difficulty,
    val examples: String
) {
    fun toDomain(): Task {
        return Task(
            id = id ?: 0,
            title = title,
            description = description,
            examples = examples,
            difficulty = difficulty,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}
