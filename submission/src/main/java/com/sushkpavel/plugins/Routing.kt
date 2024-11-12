package com.sushkpavel.plugins

import com.sushkpavel.data.model.SolutionSubmission
import com.sushkpavel.data.repo.CodeTestRepositoryImpl
import com.sushkpavel.service.Compiler
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(){
    routing {
        post("/compile") {
            val compiler = Compiler(CodeTestRepositoryImpl())
            val solution = call.receive<SolutionSubmission>()
            val result = compiler.compileSolution(solution)
            call.respond(result)
        }
    }
}