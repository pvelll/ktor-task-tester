package com.sushkpavel.application.routes.submit

import com.sushkpavel.domain.models.SolutionSubmission
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.submitRoutes(){
    post("/submit") {
        val solution = call.receive<SolutionSubmission>()
        val result = processSolution(solution)
        call.respond(HttpStatusCode.OK, result)
    }
}