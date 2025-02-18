package com.sushkpavel.domain.service

import com.sushkpavel.domain.model.SubmissionRequest
import com.sushkpavel.domain.model.TestResult

interface CheckerService {
    suspend fun checkTask(subRequest : SubmissionRequest) : TestResult
    suspend fun getTestCases(taskId: Int): List<TestCase>
}