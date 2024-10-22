package com.sushkpavel

import com.sushkpavel.models.SolutionSubmission
import com.sushkpavel.models.TestResult
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit

fun main(args : Array<String>) {
       EngineMain.main(args)
}
fun Application.module(){
    routing {
        post("/compile") {
            val solution = call.receive<SolutionSubmission>()
            val result = compileAndRun(solution)
            call.respond(result)
        }
    }
}
suspend fun compileAndRun(solution: SolutionSubmission): TestResult {
    when(solution.language){
        "C++" -> return compileCpp(solution)
        else ->{
            return TestResult(
                id = solution.id,
                userId = solution.userId,
                solutionId = solution.id,
                testId = solution.taskId,
                actualResult = "Failed to compile",
                success = false
            )
        }
    }
}

suspend fun compileCpp(solution: SolutionSubmission) : TestResult {
    val file = File("Solution.cpp")
    file.writeText(solution.code)
    val process = withContext(Dispatchers.IO) {
        ProcessBuilder("gcc", "-o", "solution", "Solution.cpp")
            .redirectErrorStream(true)
            .start()
    }
    process.waitFor(5, TimeUnit.SECONDS)
    TODO()
}
