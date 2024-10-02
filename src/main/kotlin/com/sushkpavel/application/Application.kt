package com.sushkpavel.application

import com.sushkpavel.application.plugins.configureMonitoring
import com.sushkpavel.application.plugins.configureRouting
import com.sushkpavel.application.plugins.configureSecurity
import com.sushkpavel.application.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
