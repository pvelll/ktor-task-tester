package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.tasktester.entities.checker.TestCase
import com.sushkpavel.infrastructure.model.TestCaseDTO
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import tables.TestCases
import java.time.Instant

class TestCasesRepositoryImpl(database: Database) : TestCasesRepository {


    init {
        transaction(database) {
            SchemaUtils.create(TestCases)
        }
    }


    override suspend fun getTestCasesByTaskId(taskId: Long): List<TestCase> = dbQuery {

        TestCases.selectAll().where { TestCases.taskId eq taskId }
            .map {
                TestCase(
                    id = it[TestCases.id],
                    taskId = it[TestCases.taskId],
                    input = it[TestCases.input],
                    expOutput = it[TestCases.expOutput],
                    createdAt = it[TestCases.createdAt]
                )
            }
    }

    override suspend fun postTestCases(testCaseDTO: TestCaseDTO): Int = dbQuery {

        val insertedId = TestCases.insert {
            it[taskId] = testCaseDTO.taskId
            it[input] = testCaseDTO.input
            it[expOutput] = testCaseDTO.expOutput
            it[createdAt] = Instant.now()
        } get TestCases.id
        insertedId

    }


    private suspend inline fun <T> dbQuery(crossinline block: () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}