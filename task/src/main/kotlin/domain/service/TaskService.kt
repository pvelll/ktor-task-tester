package com.sushkpavel.domain.service

import com.sushkpavel.tasktester.entities.task.Task
import com.sushkpavel.infrastructure.dto.TaskDTO
import com.sushkpavel.tasktester.entities.task.Difficulty


interface TaskService {
    suspend fun createTask(taskDTO: TaskDTO): Task
    suspend fun deleteTask(taskId: Long): Boolean
    suspend fun updateTask(taskDTO: TaskDTO): Task
    suspend fun getTask(taskId: Long): Task?
    suspend fun getTask(difficulty: Difficulty,currentTask : Long): Task?
}