package com.sushkpavel.domain

import com.sushkpavel.domain.model.TestCase

interface TestCasesRepository {
    suspend fun getTestCasesByTaskId(taskId : Int) : List<TestCase>
}