package com.sushkpavel

import com.sushkpavel.controller.configureCheckerController
import com.sushkpavel.di.executorModule
import com.sushkpavel.di.repositoryModule
import com.sushkpavel.di.serviceModule
import io.ktor.server.application.*
import io.ktor.server.netty.*
import koin.configureKoin
import security.configureSecurity
import serialization.configureSerialization

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureKoin(serviceModule, executorModule, repositoryModule)
    configureSecurity()
    configureCheckerController()
}