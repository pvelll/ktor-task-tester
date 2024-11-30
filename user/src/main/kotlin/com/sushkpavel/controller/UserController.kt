package com.sushkpavel.controller

import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureUserController() {
    val INVALID_ID = "Invalid ID"
    val userService by inject<UserService>()
    routing {
        // Create user


        // Read user
        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException(INVALID_ID)
            val user = userService.getById(id)
            if (user != null) {
                call.respond(HttpStatusCode.OK, user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // Update user
        put("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException(INVALID_ID)
            val user = call.receive<User>()
            userService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }

        // Delete user
        delete("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException(INVALID_ID)
            userService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }

}