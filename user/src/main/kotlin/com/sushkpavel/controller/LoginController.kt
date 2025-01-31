package com.sushkpavel.controller

import com.sushkpavel.domain.dto.Credentials
import com.sushkpavel.domain.dto.NotifyMessageDTO
import com.sushkpavel.domain.dto.UserDTO
import com.sushkpavel.domain.service.UserService
import com.sushkpavel.utils.getTokenFromHeader
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Application.configureLoginController() {
    val userService by inject<UserService>()
    val logger = LoggerFactory.getLogger("USER_CONTROLLER")
    routing {
        post("/register") {
            logger.info("register invoked")
            val user = call.receive<UserDTO>()
            val response = userService.register(user)?.let { _ ->
                NotifyMessageDTO(message = "Created", code = HttpStatusCode.Created.value)
            } ?: NotifyMessageDTO(message = "Unable to register", code = HttpStatusCode.BadRequest.value)

            call.respond(response)
        }

        post("/login") {
            val credentials = call.receive<Credentials>()
            val token = userService.login(credentials)
            if (token != null) {
                call.respond(HttpStatusCode.OK, token)
            } else {
                call.respond(
                    NotifyMessageDTO(
                        message = HttpStatusCode.BadRequest.toString(),
                        code = HttpStatusCode.BadRequest.value
                    )
                )
            }
        }
        authenticate {
            post("/logout") {
                val token = getTokenFromHeader(call)
                if (token != null && userService.logout(token)) {
                    call.respond(NotifyMessageDTO(message = "Logged out successfully", code = HttpStatusCode.OK.value))
                } else {
                    call.respond(
                        NotifyMessageDTO(
                            message = HttpStatusCode.Unauthorized.toString(),
                            code = HttpStatusCode.Unauthorized.value
                        )
                    )
                }
            }
        }
    }
}