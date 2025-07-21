package com.sushkpavel.domain.service

import com.sushkpavel.infrastructure.model.SubmissionRequest
import com.sushkpavel.tasktester.entities.checker.TestCase
import com.sushkpavel.infrastructure.model.TestCaseDTO
import com.sushkpavel.domain.model.TestResult

interface CheckerService {
    suspend fun checkTask(subRequest : SubmissionRequest) : TestResult
    suspend fun getTestCases(taskId: Long): List<TestCase>

    suspend fun postTestCases(testCaseDTO: TestCaseDTO) : Int
}