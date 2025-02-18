package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.domain.model.TestCase

class TestCasesRepositoryImpl : TestCasesRepository {
    override suspend fun getTestCasesByTaskId(taskId: Int): List<TestCase> {
        TODO("Not yet implemented")
    }
}