package com.sushkpavel.application.services

import com.sushkpavel.domain.models.SolutionSubmission
import com.sushkpavel.domain.models.TestResult
import com.sushkpavel.domain.services.SolutionChecker
import com.sushkpavel.infrastructure.docker.SolutionCheckerFactory

class SolutionService {
    suspend fun processSolution(solution: SolutionSubmission): TestResult {
        val checker: SolutionChecker = SolutionCheckerFactory.getChecker(solution.language)
        return checker.checkSolution(solution)
    }
}