package com.sushkpavel.domain.repository

import com.sushkpavel.domain.model.Task
import com.sushkpavel.tasktester.Difficulty

interface TaskRepository {
    suspend fun createTask(task: Task): Task
    suspend fun deleteTask(taskId: Long): Boolean
    suspend fun updateTask(task: Task): Task
    suspend fun getTask(taskId: Long): Task?
    suspend fun getTask(difficulty: Difficulty): Task?

}