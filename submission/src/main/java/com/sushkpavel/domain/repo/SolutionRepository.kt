package com.sushkpavel.domain.repo

import com.sushkpavel.tasktester.entities.submission.SolutionSubmission

interface SolutionRepository {
    suspend fun saveSubmission(submission: SolutionSubmission) : Int
    suspend fun getSubmissionById(id: Int): SolutionSubmission?
}
