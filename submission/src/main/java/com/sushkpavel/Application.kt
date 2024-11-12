package com.sushkpavel

import com.sushkpavel.plugins.configureRouting
import com.sushkpavel.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    configureSerialization()
    configureRouting()
}
