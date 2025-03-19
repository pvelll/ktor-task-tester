package com.sushkpavel.infrastructure.repository

import com.sushkpavel.com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.domain.repository.TaskRepository
import com.sushkpavel.infrastructure.dto.TaskDTO
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Database
import com.sushkpavel.domain.repository.TaskRepository.Tasks
import com.sushkpavel.domain.repository.TaskRepository.Tasks.difficulty
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.time.Instant


class TaskRepositoryImpl(database: Database) : TaskRepository {

    override suspend fun createTask(task: TaskDTO): Unit = dbQuery {
        Tasks.insert {
            it[title] = task.title
            it[description] = task.description
            it[difficulty] = task.difficulty
            it[examples] = task.examples
        }
    }

    override suspend fun deleteTask(taskId: Long): Boolean = dbQuery {
        Tasks.deleteWhere { Tasks.id eq taskId } > 0
    }

    override suspend fun updateTask(taskDTO: TaskDTO): Unit = dbQuery {
        val taskExists = Tasks.selectAll().where { Tasks.id eq taskDTO.id!! }.count() > 0
        if (!taskExists) {
            throw IllegalArgumentException("Task with id ${taskDTO.id} not found")
        }

        Tasks.update({ Tasks.id eq taskDTO.id!! }) { task ->
            task[title] = taskDTO.title
            task[description] = taskDTO.description
            task[difficulty] = taskDTO.difficulty
            task[examples] = taskDTO.examples
            task[updatedAt] = Instant.now()
        }
    }

    override suspend fun getTask(taskId: Long): Task? {
        return Tasks.selectAll().where { Tasks.id eq taskId }.map { rowToTask(it) }.singleOrNull()
    }

    override suspend fun getTask(difficulty: Difficulty): Task? {
       return Tasks.selectAll().where { Tasks.difficulty.eq(difficulty) }.map { rowToTask(it) }.singleOrNull()
    }

    private fun rowToTask(row: ResultRow): Task {
        return Task(
            id = row[Tasks.id].toLong(),
            title = row[Tasks.title],
            description = row[Tasks.description],
            examples = row[Tasks.examples],
            difficulty = row[difficulty],
            createdAt = row[Tasks.createdAt],
            updatedAt = row[Tasks.updatedAt]
        )
    }


    private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}