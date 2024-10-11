package com.sushkpavel.infrastructure.docker

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
        val file = File("docker/cpp/solution.cpp")
        file.writeText(code)
    }
//    ktor-leetcode-cpp-compiler
private fun executeTests(inputData: List<String>): List<String> {
    val results = mutableListOf<String>()
    val workingDir = File("docker/cpp").absolutePath

    inputData.forEach { input ->
        println("input : $input")
        val process = Runtime.getRuntime().exec(
            arrayOf("docker", "run", "--rm", "-v", "$workingDir:/usr/src/app", "ktor-leetcode-cpp-compiler", "./run.sh", input)
        )
        val output = process.inputStream.bufferedReader().readText().trim()
        val errorOutput = process.errorStream.bufferedReader().readText().trim()

        if (errorOutput.isNotEmpty()) {
            println("error : $errorOutput")
        }

        println("output : $output")
        results.add(output)
    }

    return results
}
}