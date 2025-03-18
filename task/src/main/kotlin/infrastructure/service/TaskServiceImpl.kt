package com.sushkpavel.infrastructure.service

import com.sushkpavel.com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.domain.service.TaskService
import com.sushkpavel.infrastructure.dto.TaskDTO

class TaskServiceImpl : TaskService {
    override suspend fun createTask(task: TaskDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskDTO: TaskDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(taskDTO: TaskDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: Long): Task? {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(difficulty: Difficulty): Task? {
        TODO("Not yet implemented")
    }
}