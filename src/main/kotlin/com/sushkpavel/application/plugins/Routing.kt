package com.sushkpavel.application.plugins

import com.sushkpavel.application.routes.submit.submitRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        submitRoutes()
    }
}
