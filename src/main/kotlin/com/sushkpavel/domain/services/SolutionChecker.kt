package com.sushkpavel.domain.services

import com.sushkpavel.domain.models.SolutionSubmission
import com.sushkpavel.domain.models.TestResult

interface SolutionChecker {
    suspend fun checkSolution(solution: SolutionSubmission): TestResult
}