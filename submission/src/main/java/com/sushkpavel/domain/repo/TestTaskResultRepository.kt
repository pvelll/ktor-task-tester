package com.sushkpavel.domain.repo

import com.sushkpavel.tasktester.entities.submission.TestResult

interface TestTaskResultRepository {
    suspend fun saveTestResult(testResult: TestResult)
    suspend fun getTestResultsBySubmissionId(submissionId: String): List<TestResult>
}
