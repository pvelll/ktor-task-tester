package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.domain.repository.TaskRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Database
import com.sushkpavel.domain.repository.TaskRepository.Tasks
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.time.Instant


class TaskRepositoryImpl(database: Database) : TaskRepository {

    init {
        transaction(database) {
            SchemaUtils.create(Tasks)
        }
    }

    override suspend fun createTask(task: Task): Task = dbQuery {
        val taskId = Tasks.insert {
            it[title] = task.title
            it[description] = task.description
            it[difficulty] = task.difficulty
            it[examples] = task.examples
            it[createdAt] = Instant.now()
            it[updatedAt] = Instant.now()
        } get Tasks.id

        Tasks.selectAll().where { Tasks.id eq taskId }
            .map { rowToTask(it) }
            .singleOrNull()
            ?: throw IllegalStateException("Failed to retrieve the inserted task")
    }

    override suspend fun deleteTask(taskId: Long): Boolean = dbQuery {
        Tasks.deleteWhere { id eq taskId } > 0
    }

    override suspend fun updateTask(task: Task): Task = dbQuery {
        val taskExists = Tasks.selectAll().where { Tasks.id eq task.id }.count() > 0
        if (!taskExists) {
            throw IllegalArgumentException("Task with id ${task.id} not found")
        }

        Tasks.update({ Tasks.id eq task.id }) {
            it[title] = task.title
            it[description] = task.description
            it[difficulty] = task.difficulty
            it[examples] = task.examples
            it[updatedAt] = Instant.now()
        }

        task
    }

    override suspend fun getTask(taskId: Long): Task? = dbQuery {
        Tasks.selectAll().where { Tasks.id eq taskId }.map { rowToTask(it) }.singleOrNull()
    }

    override suspend fun getTask(difficulty: Difficulty): Task? = dbQuery {
        val taskCount = Tasks.selectAll()
            .where { Tasks.difficulty eq difficulty }
            .count()
        if (taskCount == 0L) {
            return@dbQuery null
        }
        val randomOffset = (0 until taskCount).random()
        Tasks.selectAll()
            .where { Tasks.difficulty eq difficulty }
            .limit(1).offset(randomOffset)
            .map { rowToTask(it) }
            .singleOrNull()
    }

    private fun rowToTask(row: ResultRow): Task {
        return Task(
            id = row[Tasks.id].toLong(),
            title = row[Tasks.title],
            description = row[Tasks.description],
            examples = row[Tasks.examples],
            difficulty = row[Tasks.difficulty],
            createdAt = row[Tasks.createdAt],
            updatedAt = row[Tasks.updatedAt]
        )
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}