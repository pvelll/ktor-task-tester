package com.sushkpavel

import com.sushkpavel.models.SolutionSubmission
import com.sushkpavel.service.compileSolution
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        post("/compile") {
            val solution = call.receive<SolutionSubmission>()
            val result = compileSolution(solution)
            call.respond(result)
        }
    }
}
