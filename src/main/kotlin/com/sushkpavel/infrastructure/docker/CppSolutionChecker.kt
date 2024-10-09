package com.sushkpavel.infrastructure.docker

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.command.ExecStartResultCallback
import com.sushkpavel.domain.models.SolutionSubmission
import com.sushkpavel.domain.models.TestResult
import com.sushkpavel.domain.repositories.CodeTestRepository
import com.sushkpavel.domain.services.SolutionChecker
import java.io.File

class CppSolutionChecker(
    private val testRepo: CodeTestRepository,
) : SolutionChecker {
    override suspend fun checkSolution(solution: SolutionSubmission): TestResult {
        val tests = testRepo.getTestByTaskId(solution.taskId)
        saveCodeToFile(solution.code)
        tests?.let {
                val actualResult = executeTests(tests.inputData)

                if (actualResult != tests.expectedResult) {
                    return TestResult(
                        id = it.id,
                        userId = solution.userId,
                        solutionId = solution.id,
                        testId = it.id,
                        actualResult = "Bad result",
                        success = false
                    )
                }

        }

        return TestResult(
            id = solution.id,
            userId = solution.userId,
            solutionId = solution.id,
            testId = solution.taskId,
            actualResult = "All tests passed",
            success = true
        )
    }
    private fun saveCodeToFile(code: String) {
        val file = File("solution.cpp")
        file.writeText(code)
    }

    private fun executeTests(inputData: List<String>): List<String> {
        val results = mutableListOf<String>()

        inputData.forEach { input ->
            val process = Runtime.getRuntime().exec(
                arrayOf("docker", "run", "--rm", "-v", "$(pwd)/solution:/usr/src/app", "cpp-compiler", input)
            )
            val output = process.inputStream.bufferedReader().readText().trim()
            results.add(output)
        }

        return results
    }
}