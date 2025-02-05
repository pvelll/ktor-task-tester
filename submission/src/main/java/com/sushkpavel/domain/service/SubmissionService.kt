package com.sushkpavel.domain.service

import com.sushkpavel.domain.model.SolutionSubmission

interface SubmissionService {
    suspend fun saveSubmission(submission: SolutionSubmission)
    suspend fun getSubmission(id : Int) : SolutionSubmission?
    suspend fun checkSubmission(submission: SolutionSubmission)
}