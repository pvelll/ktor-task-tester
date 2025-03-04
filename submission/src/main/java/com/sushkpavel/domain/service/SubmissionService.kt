package com.sushkpavel.domain.service

import com.sushkpavel.domain.model.SolutionSubmission
import com.sushkpavel.domain.model.TestResult

interface SubmissionService {
    suspend fun saveSubmission(submission: SolutionSubmission) : Int
    suspend fun getSubmission(id : Int) : SolutionSubmission?
    suspend fun checkSubmission(submission: SolutionSubmission) : TestResult
}