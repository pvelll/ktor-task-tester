package com.sushkpavel.domain.service

import com.sushkpavel.com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.infrastructure.dto.TaskDTO


interface TaskService {
    suspend fun createTask(task: TaskDTO)
    suspend fun deleteTask(taskDTO: TaskDTO)
    suspend fun updateTask(taskDTO: TaskDTO)
    suspend fun getTask(taskId : Long): Task?
    suspend fun getTask(difficulty: Difficulty): Task?
}