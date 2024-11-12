package com.sushkpavel.service

import com.sushkpavel.data.model.SolutionSubmission
import com.sushkpavel.data.model.TestResult
import com.sushkpavel.domain.repo.CodeTestRepository
import kotlinx.coroutines.*
import java.io.File
import java.util.concurrent.TimeUnit


//        return TestResult("good","good","good","good","good",true)
class Compiler(private val codeTestRepository: CodeTestRepository) {

    suspend fun compileSolution(solution: SolutionSubmission): TestResult {
        return when (solution.language) {
            "C++" -> compileCpp(solution)
            else -> TestResult(
                id = solution.id,
                userId = solution.userId,
                solutionId = solution.id,
                testId = solution.taskId,
                actualResult = "Failed to compile",
                success = false
            )
        }
    }

    private suspend fun compileCpp(solution: SolutionSubmission): TestResult {
        val file = File("Solution.cpp")
        file.writeText(solution.code)
        println("Source code written to Solution.cpp:")
        println(file.readText())

        val process = withContext(Dispatchers.IO) {
            ProcessBuilder("g++", "-o", "solution", "Solution.cpp")
                .redirectErrorStream(true)
                .start()
        }
        withContext(Dispatchers.IO) {
            process.waitFor(5, TimeUnit.SECONDS)
        }
        val errorStream = process.errorStream.bufferedReader().readText()
        if (process.exitValue() != 0) {
            println("Compilation error:")
            println(errorStream)
            return TestResult(
                id = solution.id,
                userId = solution.userId,
                solutionId = solution.id,
                testId = solution.taskId,
                actualResult = errorStream,
                success = false
            )
        }

        return runTests(solution)
    }

    private suspend fun runTests(solution: SolutionSubmission): TestResult {
        val codeTest = codeTestRepository.getTestByTaskId(solution.taskId) ?: return TestResult(
            id = solution.id,
            userId = solution.userId,
            solutionId = solution.id,
            testId = solution.taskId,
            actualResult = "No tests found",
            success = false
        )

        for ((index, input) in codeTest.inputData.withIndex()) {
            val process = withContext(Dispatchers.IO) {
                ProcessBuilder("./solution")
                    .redirectErrorStream(true)
                    .start().apply {
                        outputStream.bufferedWriter().use { writer ->
                            writer.write("$input\n")
                            writer.flush()
                        }
                    }
            }
            withContext(Dispatchers.IO) {
                process.waitFor(5, TimeUnit.SECONDS)
            }
            val output = process.inputStream.bufferedReader().readText().trim()
            println("Test $index: input = '$input', expected = '${codeTest.expectedResult[index]}', got = '$output'")
            if (output != codeTest.expectedResult[index]) {
                return TestResult(
                    id = solution.id,
                    userId = solution.userId,
                    solutionId = solution.id,
                    testId = solution.taskId,
                    actualResult = "Test failed on input: $input. Expected: ${codeTest.expectedResult[index]}, but got: $output",
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
}