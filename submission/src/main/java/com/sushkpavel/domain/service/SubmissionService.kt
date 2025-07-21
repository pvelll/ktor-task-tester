package com.sushkpavel.domain.service

import com.sushkpavel.tasktester.entities.submission.SolutionSubmission
import com.sushkpavel.tasktester.entities.submission.TestResult

interface SubmissionService {
    suspend fun saveSubmission(submission: SolutionSubmission) : Int
    suspend fun getSubmission(id : Int) : SolutionSubmission?
    suspend fun checkSubmission(submission: SolutionSubmission) : TestResult
}