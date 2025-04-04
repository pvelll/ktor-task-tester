package com.sushkpavel.controller

import com.sushkpavel.domain.dto.NotifyMessageDTO
import com.sushkpavel.domain.model.Role
import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.service.UserService
import com.sushkpavel.plugins.security.UserPrincipal
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import javax.security.sasl.AuthenticationException

fun Application.configureUserController() {
    val INVALID_ID = "Invalid ID"
    val userService by inject<UserService>()

    routing {
        authenticate {
            get("/users/{id}") {
                val principal = call.principal<UserPrincipal>() ?: throw AuthenticationException()
                val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException(INVALID_ID)

                if (principal.role == Role.ADMIN || principal.userId == id) {
                    val user = userService.getById(id)
                    if (user != null) {
                        call.respond(HttpStatusCode.OK, user)
                    } else {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            message = NotifyMessageDTO(
                                message = HttpStatusCode.NotFound.description,
                                code = HttpStatusCode.NotFound.value
                            )
                        )
                    }
                } else {
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = NotifyMessageDTO(
                            message = HttpStatusCode.Forbidden.description,
                            code = HttpStatusCode.Forbidden.value
                        )
                    )
                }
            }

            put("/users/{id}") {
                val principal = call.principal<UserPrincipal>() ?: throw AuthenticationException()
                val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException(INVALID_ID)

                if (principal.role == Role.ADMIN || principal.userId == id) {
                    val user = call.receive<User>()
                    userService.update(id, user)
                    call.respond(
                        HttpStatusCode.OK,
                        message = NotifyMessageDTO(
                            message = HttpStatusCode.OK.description,
                            code = HttpStatusCode.OK.value
                        )
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = NotifyMessageDTO(
                            message = HttpStatusCode.Forbidden.description,
                            code = HttpStatusCode.Forbidden.value
                        )
                    )
                }
            }

            delete("/users/{id}") {
                val principal = call.principal<UserPrincipal>() ?: throw AuthenticationException()
                val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException(INVALID_ID)

                if (principal.role == Role.ADMIN || principal.userId == id) {
                    userService.delete(id)
                    call.respond(
                        HttpStatusCode.OK,
                        message = NotifyMessageDTO(
                            message = HttpStatusCode.OK.description,
                            code = HttpStatusCode.OK.value
                        )
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.Forbidden,
                        message = NotifyMessageDTO(
                            message = HttpStatusCode.Forbidden.description,
                            code = HttpStatusCode.Forbidden.value
                        )
                    )
                }
            }
        }
    }
}

