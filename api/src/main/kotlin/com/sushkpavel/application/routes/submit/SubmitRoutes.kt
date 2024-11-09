package com.sushkpavel.application.routes.submit

import com.sushkpavel.application.client.CompileClient
import com.sushkpavel.domain.models.TestResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.submitRoutes() {
    post("/submit") {
        val solution = call.receive<com.sushkpavel.domain.models.SolutionSubmission>()
        val client = CompileClient()
        val result = client.compileSolution(solution)
        println("Compilation result: ${result.actualResult}, Success: ${result.success}")
        call.respond(
            HttpStatusCode.OK,
            TestResult(result.id, result.userId, result.solutionId, result.testId, result.actualResult, result.success)
        )
    }
}
//
//suspend fun sendSolutionToCompileService(solution: SolutionSubmission): TestResult {
//    val channel = ManagedChannelBuilder.forAddress("compile-com.sushkpavel.service", 8083)
//        .usePlaintext()
//        .build()
//    val stub = CompileServiceGrpc.newBlockingStub(channel)
//    return stub.compileSolution(solution)
//}