package com.sushkpavel

import com.sushkpavel.controller.configureLoginController
import com.sushkpavel.controller.configureUserController
import com.sushkpavel.di.userModule
import com.sushkpavel.plugins.*
import security.configureSecurity
import io.ktor.server.application.*
import koin.configureKoin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin(userModule)
    configureSecurity()
    configureUserController()
    configureLoginController()
    configureSerialization()
}

