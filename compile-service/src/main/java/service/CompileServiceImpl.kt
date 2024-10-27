package com.sushkpavel.service

import com.sushkpavel.CompileServiceGrpc
import com.sushkpavel.models.SolutionSubmission
import com.sushkpavel.models.TestResult
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit


class CompileServiceImpl : CompileServiceGrpc.CompileServiceImplBase() {

    override fun compileSolution(
        request: com.sushkpavel.SolutionSubmission?,
        responseObserver: StreamObserver<com.sushkpavel.TestResult>?
    ) {
        println("got request : ${request?.id} ")
        val solution = SolutionSubmission(
            id = request?.id ?: "",
            userId = request?.userId ?: "",
            taskId = request?.taskId ?: "",
            code = request?.code ?: "",
            language = request?.language ?: "",
            input = request?.input ?: "",
            status = request?.status ?: ""
        )
        val result = runBlocking { compileAndRun(solution) }
        responseObserver?.onNext(
            com.sushkpavel.TestResult.newBuilder()
                .setId(result.id)
                .setUserId(result.userId)
                .setSolutionId(result.solutionId)
                .setTestId(result.testId)
                .setActualResult(result.actualResult)
                .setSuccess(result.success)
                .build()
        )
        responseObserver?.onCompleted()
    }

    private suspend fun compileAndRun(solution: SolutionSubmission): TestResult {
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
        val process = withContext(Dispatchers.IO) {
            ProcessBuilder("gcc", "-o", "solution", "Solution.cpp")
                .redirectErrorStream(true)
                .start()
        }
        process.waitFor(5, TimeUnit.SECONDS)
        val errorStream = process.errorStream.bufferedReader().readText()
        return if (process.exitValue() == 0) {
            TestResult(
                id = solution.id,
                userId = solution.userId,
                solutionId = solution.id,
                testId = solution.taskId,
                actualResult = "Compilation successful",
                success = true
            )
        } else {
            TestResult(
                id = solution.id,
                userId = solution.userId,
                solutionId = solution.id,
                testId = solution.taskId,
                actualResult = errorStream,
                success = false
            )
        }
    }
}