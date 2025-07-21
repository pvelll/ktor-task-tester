package com.sushkpavel.domain

import com.sushkpavel.tasktester.entities.checker.TestCase
import com.sushkpavel.infrastructure.model.TestCaseDTO

interface TestCasesRepository {
    suspend fun getTestCasesByTaskId(taskId: Long): List<TestCase>
    suspend fun postTestCases(testCaseDTO: TestCaseDTO): Int
}