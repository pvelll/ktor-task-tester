package com.sushkpavel

import com.sushkpavel.controller.configureController
import com.sushkpavel.di.configureKoin
import com.sushkpavel.plugins.*
import com.sushkpavel.plugins.security.configureSecurity
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureController()
    configureKoin()
    configureSerialization()
}
