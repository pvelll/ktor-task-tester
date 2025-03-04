package com.sushkpavel.controller

import com.sushkpavel.domain.dto.SubmissionRequest
import com.sushkpavel.domain.model.SolutionSubmission
import com.sushkpavel.domain.service.SubmissionService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.sushkpavel.plugins.security.UserPrincipal
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.configureSubmissionController() {
    val solutionService by inject<SubmissionService>()
    routing {
        route("/submit") {
            authenticate {
                post {
                    val submissionRequest = call.receive<SubmissionRequest>()
                    val principal = call.principal<UserPrincipal>() ?: run {
                        call.respond(HttpStatusCode.Unauthorized, "User is not logged in")
                        return@post
                    }
                    val submission = SolutionSubmission(
                        userId = principal.userId,
                        taskId = submissionRequest.taskId,
                        code = submissionRequest.code,
                        language = submissionRequest.language,
                        createdAt = Instant.now()
                    )
                    solutionService.saveSubmission(submission)
                    val testResult = solutionService.checkSubmission(submission)
                    call.respond(testResult)
                }
            }
        }
    }
}
