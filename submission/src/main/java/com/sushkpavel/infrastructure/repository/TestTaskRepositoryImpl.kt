package com.sushkpavel.infrastructure.repository

import com.sushkpavel.infrastructure.dto.SubmissionRequest
import com.sushkpavel.infrastructure.dto.TestResultDTO
import com.sushkpavel.tasktester.entities.submission.SolutionSubmission
import com.sushkpavel.tasktester.entities.submission.TestResult
import com.sushkpavel.domain.repo.TestTaskRepository
import io.ktor.client.*
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import tables.TestResults


class TestTaskRepositoryImpl(private val client: HttpClient, database: Database) : TestTaskRepository {


    init {
        transaction(database) {
            SchemaUtils.create(TestResults)
        }
    }

    override suspend fun testTask(submissionRequest: SubmissionRequest): TestResultDTO {
        try {
            val response = client.post("https://nginx/checker/check-solution") {
                contentType(ContentType.Application.Json)
                setBody(submissionRequest)
            }.bodyAsText()
            return Json.decodeFromString<TestResultDTO>(response)
        } catch (e: HttpRequestTimeoutException) {
            return TestResultDTO(
                testId = submissionRequest.taskId.toInt(),
                actualResult = "Execution timeout",
                success = false
            )
        }
    }

    override suspend fun saveResult(testResultDTO: TestResultDTO, submission: SolutionSubmission): Unit = dbQuery {
        TestResults.insert {
            it[userId] = submission.userId
            it[taskId] = submission.taskId
            it[submissionId] = submission.id!!
            it[actualResult] = testResultDTO.actualResult
            it[success] = testResultDTO.success
        }
    }

    override suspend fun getResultsForTask(userId: Int, taskId: Long): List<TestResult?> = dbQuery {
        TestResults.selectAll().where { (TestResults.userId eq userId) and (TestResults.taskId eq taskId) }
            .mapNotNull { rowToTestResult(it) }
    }

    override suspend fun getResultForTask(userId: Int, submissionId: Int): TestResult? = dbQuery{
        TestResults.selectAll().where { (TestResults.userId eq userId) and (TestResults.submissionId eq submissionId) }
            .map { rowToTestResult(it) }
            .singleOrNull()
    }

    private fun rowToTestResult(row: ResultRow): TestResult {
        return TestResult(
            id = row[TestResults.id],
            userId = row[TestResults.userId],
            submissionId = row[TestResults.submissionId],
            taskId = row[TestResults.taskId],
            actualResult = row[TestResults.actualResult],
            success = row[TestResults.success],
            createdAt = row[TestResults.createdAt]
        )
    }

    private suspend inline fun <T> dbQuery(crossinline block: () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}
