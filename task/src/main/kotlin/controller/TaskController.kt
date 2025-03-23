package com.sushkpavel.controller

import com.sushkpavel.domain.model.Difficulty
import com.sushkpavel.domain.model.Task
import com.sushkpavel.domain.service.TaskService
import com.sushkpavel.infrastructure.dto.TaskDTO
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
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
            authenticate {
                post {
                    try {
                        val taskDTO = call.receive<TaskDTO>()
                        val createdTask = taskService.createTask(taskDTO)
                        call.respond(HttpStatusCode.Created, createdTask)
                    } catch (e: ContentTransformationException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid task format")
                    } catch (e: Exception) {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            "Error creating task: ${e.message}"
                        )
                    }
                }

                put {
                    try {
                        val taskDTO = call.receive<TaskDTO>()
                        if (taskDTO.id == null) {
                            call.respond(HttpStatusCode.BadRequest, "Task ID is required for update")
                            return@put
                        }

                        taskService.updateTask(taskDTO)?.let {
                            call.respond(it)
                        } ?: call.respond(HttpStatusCode.NotFound)
                    } catch (e: ContentTransformationException) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid task format")
                    } catch (e: Exception) {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            "Error updating task: ${e.message}"
                        )
                    }
                }

                delete {
                    val taskId = call.parameters["taskId"]?.toLongOrNull()
                        ?: return@delete call.respond(
                            HttpStatusCode.BadRequest,
                            "Task ID parameter is required"
                        )

                    val isDeleted = taskService.deleteTask(taskId)
                    if (isDeleted) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}