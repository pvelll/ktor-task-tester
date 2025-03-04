package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.dto.SubmissionRequest
import com.sushkpavel.domain.model.SolutionSubmission
import com.sushkpavel.domain.model.TestResult
import com.sushkpavel.domain.repo.SolutionRepository
import com.sushkpavel.domain.repo.TestTaskRepository
import com.sushkpavel.domain.service.SubmissionService

class SubmissionServiceImpl(
    private val solutionRepository: SolutionRepository,
    private val testTaskRepository: TestTaskRepository
) : SubmissionService {

    override suspend fun saveSubmission(submission: SolutionSubmission) = solutionRepository.saveSubmission(submission)

    override suspend fun getSubmission(id: Int): SolutionSubmission? {
        return solutionRepository.getSubmissionById(id)
    }

    override suspend fun checkSubmission(submission: SolutionSubmission) : TestResult {
        val submissionRequest = SubmissionRequest(submission.taskId,submission.code,submission.language)
        val resultDTO = testTaskRepository.testTask(submissionRequest)
        testTaskRepository.saveResult(resultDTO, submission)
        val testResult =
            submission.id?.let { testTaskRepository.getResultForTask(userId = submission.userId, submissionId = it) }
        return testResult!!
    }
}