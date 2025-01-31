package com.sushkpavel.domain.repo

import com.sushkpavel.domain.model.SolutionSubmission

interface SolutionRepository {
    suspend fun saveSubmission(submission: SolutionSubmission)
    suspend fun getSubmissionById(id: String): SolutionSubmission?
}
