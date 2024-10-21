package com.sushkpavel

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8084) {
        routing {
            post("/check-solution") {
                call.respond("result")
            }
        }
    }.start(wait = true)
}