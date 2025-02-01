package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.model.SolutionSubmission
import com.sushkpavel.domain.repo.SolutionRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class SolutionRepositoryImpl(database: Database) : SolutionRepository {
    object Submissions : Table() {
        val id = integer("id").autoIncrement()
        val userId = integer("user_id")
        val taskId = integer("task_id")
        val code = text("code")
        val language = varchar("language", length = 50)
        val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp)

        override val primaryKey = PrimaryKey(id)

        init {
            uniqueIndex(id)
        }

    }

    init {
        transaction(database) {
            SchemaUtils.create(Submissions)
        }
    }

    override suspend fun saveSubmission(submission: SolutionSubmission): Unit = dbQuery {
        Submissions.insert {
            it[userId] = submission.userId
            it[taskId] = submission.taskId
            it[code] = submission.code
            it[language] = submission.language
            it[createdAt] = Instant.now()
        }
    }

    override suspend fun getSubmissionById(id: Int): SolutionSubmission? = dbQuery {
        Submissions.selectAll().where { Submissions.id eq id }
            .map { rowToSubmission(it) }
            .singleOrNull()
    }

    private fun rowToSubmission(row: ResultRow): SolutionSubmission {
        return SolutionSubmission(
            id = row[Submissions.id],
            userId = row[Submissions.userId],
            taskId = row[Submissions.taskId],
            code = row[Submissions.code],
            language = row[Submissions.language],
            createdAt = Instant.now()
        )
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
