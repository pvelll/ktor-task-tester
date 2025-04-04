package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.domain.repository.TaskRepository
import com.sushkpavel.domain.service.TaskService
import com.sushkpavel.infrastructure.dto.TaskDTO

class TaskServiceImpl(private val taskRepository: TaskRepository) : TaskService {

    override suspend fun createTask(taskDTO: TaskDTO): Task {
        val task = taskDTO.toDomain() // Преобразуем DTO в доменный объект
        return taskRepository.createTask(task)
    }

    override suspend fun deleteTask(taskId: Long): Boolean {
        return taskRepository.deleteTask(taskId)
    }

    override suspend fun updateTask(taskDTO: TaskDTO): Task {
        val task = taskDTO.toDomain() // Преобразуем DTO в доменный объект
        return taskRepository.updateTask(task)
    }

    override suspend fun getTask(taskId: Long): Task? {
        return taskRepository.getTask(taskId)
    }

    override suspend fun getTask(difficulty: Difficulty): Task? {
        return taskRepository.getTask(difficulty)
    }
}