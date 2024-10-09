package com.sushkpavel.application

import com.sushkpavel.application.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.Level

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
