package com.sushkpavel.controller

import com.sushkpavel.domain.model.SubmissionRequest
import com.sushkpavel.domain.model.TestCaseDTO
import com.sushkpavel.domain.service.CheckerService
import com.sushkpavel.infrastructure.service.CheckerServiceImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.qualifier.Qualifier
import org.koin.ktor.ext.inject

fun Application.configureCheckerController(){
    val checkerService by inject<CheckerService>()
    routing {
        post("/check-solution") {
            val checkRequest = call.receive<SubmissionRequest>()

        }
        post("/submit-task"){
            println("test case ")
            val taskDto = call.receive<TestCaseDTO>()
            val testCase = checkerService.postTestCases(taskDto)
            call.respond(testCase)
        }
    }
}
