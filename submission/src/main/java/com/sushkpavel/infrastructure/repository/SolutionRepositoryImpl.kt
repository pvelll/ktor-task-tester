package com.sushkpavel.infrastructure.repository

import com.sushkpavel.tasktester.entities.submission.SolutionSubmission
import com.sushkpavel.domain.repo.SolutionRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import tables.Submissions
import java.time.Instant

class SolutionRepositoryImpl(database: Database) : SolutionRepository {

    init {
        transaction(database) {
            SchemaUtils.create(Submissions)
        }
    }

    override suspend fun saveSubmission(submission: SolutionSubmission): Int = dbQuery {
        val insertedId = Submissions.insert {
            it[userId] = submission.userId
            it[taskId] = submission.taskId
            it[code] = submission.code
            it[language] = submission.language
            it[createdAt] = Instant.now()
        } get Submissions.id // Получаем ID вставленной записи

        insertedId
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
            createdAt = row[Submissions.createdAt]
        )
    }

    private suspend inline fun <T> dbQuery(crossinline block: () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
