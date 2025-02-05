package com.sushkpavel.domain.repo

import com.sushkpavel.domain.dto.SubmissionRequest
import com.sushkpavel.domain.model.TestResult

interface TestTaskRepository {
    suspend fun testTask(submissionRequest: SubmissionRequest) : TestResult
}