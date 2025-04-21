package com.sushkpavel.controller

import com.sushkpavel.infrastructure.model.SubmissionRequest
import com.sushkpavel.infrastructure.model.TestCaseDTO
import com.sushkpavel.domain.service.CheckerService
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCheckerController() {
    val checkerService by inject<CheckerService>()
    routing {
        post("/check-solution") {
            val checkRequest = call.receive<SubmissionRequest>()
            val testResult = checkerService.checkTask(checkRequest)
            call.respond(testResult)
        }
        authenticate {
            post("/submit-task") {
                println("test case ")
                val taskDto = call.receive<TestCaseDTO>()
                val testCase = checkerService.postTestCases(taskDto)
                call.respond(testCase)
            }
        }
    }
}
