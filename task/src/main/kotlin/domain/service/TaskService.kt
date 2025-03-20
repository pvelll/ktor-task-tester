package com.sushkpavel.domain.service

import com.sushkpavel.com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.infrastructure.dto.TaskDTO


interface TaskService {
    suspend fun createTask(taskDTO: TaskDTO): Task
    suspend fun deleteTask(taskId: Long): Boolean
    suspend fun updateTask(taskDTO: TaskDTO): Task
    suspend fun getTask(taskId: Long): Task?
    suspend fun getTask(difficulty: Difficulty): Task?
}