package com.sushkpavel.domain.repo

import com.sushkpavel.domain.model.TestResult

interface TestResultRepository {
    suspend fun saveTestResult(testResult: TestResult)
    suspend fun getTestResultsBySubmissionId(submissionId: String): List<TestResult>
}
