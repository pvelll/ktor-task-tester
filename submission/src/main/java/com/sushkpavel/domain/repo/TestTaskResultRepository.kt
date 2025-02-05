package com.sushkpavel.domain.repo

import com.sushkpavel.domain.model.TestResult

interface TestTaskResultRepository {
    suspend fun saveTestResult(testResult: TestResult)
    suspend fun getTestResultsBySubmissionId(submissionId: String): List<TestResult>
}
