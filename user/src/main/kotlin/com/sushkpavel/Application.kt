package com.sushkpavel

import com.sushkpavel.controller.configureLoginController
import com.sushkpavel.controller.configureUserController
import com.sushkpavel.di.configureKoin
import com.sushkpavel.plugins.*
import security.configureSecurity
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureUserController()
    configureLoginController()
    configureSerialization()
}

