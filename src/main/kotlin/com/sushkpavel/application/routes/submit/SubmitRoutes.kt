package com.sushkpavel.application.routes.submit

import com.sushkpavel.application.services.SolutionService
import com.sushkpavel.domain.models.SolutionSubmission
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.submitRoutes() {
    val solutionService by inject<SolutionService>()
    post("/submit") {
//        try {
            val solution = call.receive<SolutionSubmission>()
            println(solution)
            val result = solutionService.processSolution(solution)
            call.respond(HttpStatusCode.OK, result)
//
//        } catch (e: Exception) {
//            call.respond(HttpStatusCode.InternalServerError, "Error processing solution submission")
//        }
    }
}