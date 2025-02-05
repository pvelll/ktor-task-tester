package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.model.SolutionSubmission
import com.sushkpavel.domain.repo.SolutionRepository
import com.sushkpavel.domain.service.SubmissionService

class SubmissionServiceImpl(
    private val solutionRepository: SolutionRepository
) : SubmissionService {
    override suspend fun saveSubmission(submission: SolutionSubmission) {
            solutionRepository.saveSubmission(submission)
    }

    override suspend fun getSubmission(id: Int): SolutionSubmission? {
        return solutionRepository.getSubmissionById(id)
    }

    override suspend fun checkSubmission(submission: SolutionSubmission)  {

    }
}