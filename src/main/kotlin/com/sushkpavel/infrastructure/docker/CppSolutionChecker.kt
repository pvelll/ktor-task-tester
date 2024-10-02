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
    private val containerId: String,
    private val dockerClient: DockerClient
) : SolutionChecker {
    override suspend fun checkSolution(solution: SolutionSubmission): TestResult {
        val tests = testRepo.getTestByTaskId(solution.taskId)

        tests?.let {
            for (i in it.inputData.indices) {
                val inputData = it.inputData[i]
                val expectedResult = it.expectedResult[i]
                val actualResult = runDockerContainer(solution.code, inputData)

                if (actualResult.trim() != expectedResult.trim()) {
                    return TestResult(
                        id = it.id,
                        userId = solution.userId,
                        solutionId = solution.id,
                        testId = it.id,
                        actualResult = actualResult,
                        success = false
                    )
                }
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

    private fun runDockerContainer(code: String, inputData: String): String {
        val tempDir = createTempDir()
        val codeFile = File(tempDir, "solution.cpp")
        val inputFile = File(tempDir, "input.txt")
        val outputFile = File(tempDir, "output.txt")

        codeFile.writeText(code)
        inputFile.writeText(inputData)

        try {
            dockerClient.copyArchiveToContainerCmd(containerId)
                .withHostResource(tempDir.absolutePath)
                .withRemotePath("/app")
                .exec()

            val execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withCmd("sh", "-c", "g++ -o /app/solution /app/solution.cpp && /app/solution < /app/input.txt > /app/output.txt")
                .exec()

            dockerClient.execStartCmd(execCreateCmdResponse.id)
                .exec(ExecStartResultCallback(System.out, System.err))
                .awaitCompletion()

            dockerClient.copyArchiveFromContainerCmd(containerId, "/app/output.txt")
                .withHostPath(outputFile.absolutePath)
                .exec()

            return outputFile.readText()
        } finally {
            codeFile.delete()
            inputFile.delete()
            outputFile.delete()
            tempDir.delete()
        }
    }
}