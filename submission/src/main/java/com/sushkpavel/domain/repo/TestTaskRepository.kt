package com.sushkpavel.domain.repo

import com.sushkpavel.infrastructure.dto.SubmissionRequest
import com.sushkpavel.infrastructure.dto.TestResultDTO
import com.sushkpavel.tasktester.entities.submission.SolutionSubmission
import com.sushkpavel.tasktester.entities.submission.TestResult

interface TestTaskRepository {
    suspend fun testTask(submissionRequest: SubmissionRequest) : TestResultDTO
    suspend fun saveResult(testResultDTO: TestResultDTO, submission: SolutionSubmission)
    suspend fun getResultsForTask(userId : Int, taskId : Long) : List<TestResult?>
    suspend fun getResultForTask(userId : Int, submissionId : Int) : TestResult?
}