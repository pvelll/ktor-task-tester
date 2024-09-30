package com.sushkpavel.routes.submit

import io.ktor.server.routing.*

fun Route.submitRoutes(){
    post("/submit") {
        val solution = call.receive<SolutionSubmission>()
        val result = processSolution(solution)
        call.respond(HttpStatusCode.OK, result)
    }
}