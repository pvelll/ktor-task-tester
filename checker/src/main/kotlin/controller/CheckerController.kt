package com.sushkpavel.controller

import com.sushkpavel.domain.model.SubmissionRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureCheckerController(){
    routing {
        post("/check-solution") {
            val checkRequest = call.receive<SubmissionRequest>()

        }
    }
}
