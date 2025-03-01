package com.sushkpavel.domain

import com.sushkpavel.domain.model.TestCase
import com.sushkpavel.domain.model.TestCaseDTO

interface TestCasesRepository {
    suspend fun getTestCasesByTaskId(taskId: Int): List<TestCase>
    suspend fun postTestCases(testCaseDTO: TestCaseDTO): Int
}