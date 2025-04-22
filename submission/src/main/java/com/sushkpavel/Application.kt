package com.sushkpavel

import com.sushkpavel.di.configureKoin
import com.sushkpavel.plugins.configureRouting
import com.sushkpavel.plugins.configureSerialization
import io.ktor.server.application.*
import security.configureSecurity

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    configureKoin()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
