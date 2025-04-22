package com.sushkpavel

import com.sushkpavel.controller.configureCheckerController
import com.sushkpavel.di.configureKoin
import com.sushkpavel.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*
import security.configureSecurity

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureKoin()
    configureSecurity()
    configureCheckerController()
}