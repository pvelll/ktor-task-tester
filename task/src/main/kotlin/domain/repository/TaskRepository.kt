package com.sushkpavel.domain.repository

import com.sushkpavel.com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

interface TaskRepository {
    suspend fun createTask(task: Task): Task
    suspend fun deleteTask(taskId: Long): Boolean
    suspend fun updateTask(task: Task): Task
    suspend fun getTask(taskId: Long): Task?
    suspend fun getTask(difficulty: Difficulty): Task?

    object Tasks : Table(){
        val id = long("id").autoIncrement()
        val title = varchar("title", 64)
        val description = text("description")
        val difficulty = enumerationByName("difficulty", 50, Difficulty::class)
        val examples = text("examples")
        val createdAt = timestamp("created_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)
        val updatedAt = timestamp("updated_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)

        override val primaryKey = PrimaryKey(id)
    }
}