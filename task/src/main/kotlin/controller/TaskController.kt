package com.sushkpavel.controller

import com.sushkpavel.infrastructure.dto.NotifyMessageDTO
import com.sushkpavel.tasktester.entities.task.Task
import com.sushkpavel.domain.service.TaskService
import com.sushkpavel.infrastructure.dto.TaskDTO
import com.sushkpavel.tasktester.entities.task.Difficulty
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
                            call.respond(
                                HttpStatusCode.BadRequest,
                                NotifyMessageDTO("Invalid difficulty value", HttpStatusCode.BadRequest.value)
                            )
                            return@get
                        }
                    }

                    else -> {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            NotifyMessageDTO(
                                "Missing parameters: taskId or difficulty",
                                HttpStatusCode.BadRequest.value
                            )
                        )
                        return@get
                    }
                }

                if (task != null) {
                    call.respond(task)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        NotifyMessageDTO("Task not found", HttpStatusCode.NotFound.value)
                    )
                }
            }
            authenticate {
                post {
                    try {
                        val taskDTO = call.receive<TaskDTO>()
                        val createdTask = taskService.createTask(taskDTO)
                        call.respond(HttpStatusCode.Created, createdTask)
                    } catch (e: ContentTransformationException) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            NotifyMessageDTO("Invalid task format", HttpStatusCode.BadRequest.value)
                        )
                    } catch (e: Exception) {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            NotifyMessageDTO(e.message ?: "Unknown error", HttpStatusCode.InternalServerError.value)
                        )
                    }
                }

                put {
                    try {
                        val taskDTO = call.receive<TaskDTO>()
                        if (taskDTO.id == null) {
                            call.respond(
                                HttpStatusCode.BadRequest, NotifyMessageDTO(
                                    "Task ID is required for update",
                                    HttpStatusCode.BadRequest.value
                                )
                            )
                            return@put
                        }

                        taskService.updateTask(taskDTO)?.let {
                            call.respond(it)
                        } ?: call.respond(HttpStatusCode.NotFound)
                    } catch (e: ContentTransformationException) {
                        call.respond(
                            HttpStatusCode.BadRequest, NotifyMessageDTO(
                                "Invalid task format",
                                HttpStatusCode.BadRequest.value
                            )
                        )
                    } catch (e: Exception) {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            NotifyMessageDTO(
                                "Error updating task: ${e.message}",
                                HttpStatusCode.BadRequest.value
                            )

                        )
                    }
                }

                delete {
                    val taskId = call.parameters["taskId"]?.toLongOrNull()
                        ?: return@delete call.respond(
                            HttpStatusCode.BadRequest,
                            NotifyMessageDTO(
                                "Task ID parameter is required",
                                HttpStatusCode.BadRequest.value
                            )


                        )

                    val isDeleted = taskService.deleteTask(taskId)
                    if (isDeleted) {
                        call.respond(HttpStatusCode.OK)
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound,
                            NotifyMessageDTO("Task not found", HttpStatusCode.NotFound.value)
                        )
                    }
                }
            }
        }
    }
}