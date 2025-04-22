package com.sushkpavel


import com.sushkpavel.controller.configureTaskController
import com.sushkpavel.di.repositoryModule
import com.sushkpavel.di.serviceModule
import com.sushkpavel.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*
import koin.configureKoin
import security.configureSecurity

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureKoin(repositoryModule, serviceModule)
    configureSecurity()
    configureTaskController()
}