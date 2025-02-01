package com.sushkpavel.controller

import com.sushkpavel.domain.dto.SubmissionRequest
import com.sushkpavel.domain.model.SolutionSubmission
import com.sushkpavel.domain.repo.SolutionRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.sushkpavel.plugins.security.UserPrincipal
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.configureSubmissionController(){
    val solutionRepository by inject<SolutionRepository>()
    routing {
        route("/submit") {
            authenticate {
                post {
                    val submissionRequest = call.receive<SubmissionRequest>()
                    val principal = call.principal<UserPrincipal>()

                    val submission = principal?.let { it1 ->
                        SolutionSubmission(
                            userId = it1.userId,
                            taskId = submissionRequest.taskId,
                            code = submissionRequest.code,
                            language = submissionRequest.language,
                            createdAt = Instant.now()
                        )
                    }

                    if (submission != null) {
                        solutionRepository.saveSubmission(submission)
                    }


                    //TODO: Neccesary logic for check task up
//                    val testResults = sendToCheckerService(submission)

//                    testResults.forEach { testResult ->
//                        testResultRepository.saveTestResult(testResult)
//                    }

                    call.respond(
                        status = HttpStatusCode.OK,
                        message = "TEST"
                    )
                }
            }
        }
    }
}
