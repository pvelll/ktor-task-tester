package com.sushkpavel.infrastructure.persistance

import com.sushkpavel.domain.models.CodeTest
import com.sushkpavel.domain.repositories.CodeTestRepository

class CodeTestRepositoryImpl : CodeTestRepository {
    override suspend fun getTestByTaskId(id: String): CodeTest? {
        return CodeTest(
            id = "1",
            taskId = "1",
            inputData = listOf("1 1", "2 2", "3 3", "4 4", "5 5"),
            expectedResult = listOf("2", "4", "6", "8", "10")
        )
    }
}