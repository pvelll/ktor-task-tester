package com.sushkpavel.service

import com.sushkpavel.models.SolutionSubmission
import com.sushkpavel.models.TestResult
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit




suspend fun compileSolution(solution: SolutionSubmission): TestResult {
        return TestResult("good","good","good","good","good",true)
//    return when (solution.language) {
//        "C++" -> compileCpp(solution)
//        else -> TestResult(
//            id = solution.id,
//            userId = solution.userId,
//            solutionId = solution.id,
//            testId = solution.taskId,
//            actualResult = "Failed to compile",
//            success = false
//        )
//    }
}

suspend fun compileCpp(solution: SolutionSubmission): TestResult {
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