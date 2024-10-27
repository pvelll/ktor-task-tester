package com.sushkpavel.application.routes.submit

import com.sushkpavel.SolutionSubmission
import com.sushkpavel.application.grpc.CompileClient
import com.sushkpavel.domain.models.TestResult
import io.grpc.ManagedChannelBuilder
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.submitRoutes() {
    post("/submit") {
        val solution = call.receive<com.sushkpavel.domain.models.SolutionSubmission>()
        val channel = ManagedChannelBuilder.forAddress("compile-service", 8083)
            .usePlaintext()
            .build()

        val client = CompileClient(channel)

        val solutionGrpc = SolutionSubmission.newBuilder()
            .setId(solution.id)
            .setUserId(solution.userId)
            .setTaskId(solution.taskId)
            .setCode(solution.code)
            .setLanguage(solution.language)
            .setInput(solution.input)
            .setStatus(solution.status)
            .build()

        val result = client.compileSolution(solutionGrpc)
        println("Compilation result: ${result.actualResult}, Success: ${result.success}")
        call.respond(
            HttpStatusCode.OK,
            TestResult(result.id, result.userId, result.solutionId, result.testId, result.actualResult, result.success)
        )
        channel.shutdown()
    }
}
//
//suspend fun sendSolutionToCompileService(solution: SolutionSubmission): TestResult {
//    val channel = ManagedChannelBuilder.forAddress("compile-service", 8083)
//        .usePlaintext()
//        .build()
//    val stub = CompileServiceGrpc.newBlockingStub(channel)
//    return stub.compileSolution(solution)
//}