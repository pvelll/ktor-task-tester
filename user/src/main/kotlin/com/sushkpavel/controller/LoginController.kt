package com.sushkpavel.controller

import com.sushkpavel.infrastructure.dto.Credentials
import com.sushkpavel.infrastructure.dto.NotifyMessageDTO
import com.sushkpavel.infrastructure.dto.UserDTO
import entities.user.Role
import com.sushkpavel.domain.service.UserService
import com.sushkpavel.utils.getTokenFromHeader
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.configureLoginController() {
    val userService by inject<UserService>()
    routing {
        post("/register") {
            val user = call.receive<UserDTO>()
            val adminKey = call.request.headers["X-admin-key"]
            val isAdminRegistration = adminKey == "hardcodeAdminKey"
            if (user.role == Role.ADMIN && !isAdminRegistration) {
                call.respond("FUCK YOU")
                return@post
            }
            when (userService.register(user)) {
                HttpStatusCode.Created.value -> {
                    call.respond(
                        HttpStatusCode.Created,
                        NotifyMessageDTO(message = "Created", code = HttpStatusCode.Created.value)
                    )
                }

                else -> {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        NotifyMessageDTO(message = "Unable to register", code = HttpStatusCode.BadRequest.value)
                    )
                }

            }
        }

        post("/login") {
            val credentials = call.receive<Credentials>()
            val token = userService.login(credentials)
            if (token != null) {
                call.respond(HttpStatusCode.OK, token)
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    NotifyMessageDTO(
                        message = "Wrong password or login",
                        code = HttpStatusCode.Unauthorized.value
                    )
                )
            }
        }
        authenticate {
            post("/logout") {
                val token = getTokenFromHeader(call)
                if (token != null && userService.logout(token)) {
                    call.respond(HttpStatusCode.OK,NotifyMessageDTO(message = "Logged out successfully", code = HttpStatusCode.OK.value))
                } else {
                    call.respond(
                        HttpStatusCode.Unauthorized,
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