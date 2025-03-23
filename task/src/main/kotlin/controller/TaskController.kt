package com.sushkpavel.controller

import com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.domain.service.TaskService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureTaskController() {
    val taskService: TaskService by inject()

    routing {
        route("/task") {
            get {
                val taskId = call.parameters["taskId"]?.toLongOrNull()
                val difficultyStr = call.parameters["difficulty"]?.uppercase()

                val task = when {
                    taskId != null -> taskService.getTask(taskId)
                    difficultyStr != null -> {
                        try {
                            taskService.getTask(Difficulty.valueOf(difficultyStr))
                        } catch (e: IllegalArgumentException) {
                            call.respond(HttpStatusCode.BadRequest, "Invalid difficulty value")
                            return@get
                        }
                    }
                    else -> {
                        call.respond(HttpStatusCode.BadRequest, "Missing parameters: taskId or difficulty")
                        return@get
                    }
                }

                if (task != null) {
                    call.respond(task)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            // Остальные методы (POST, PUT, DELETE) можно добавить здесь
        }
    }
}