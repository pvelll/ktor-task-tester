package com.sushkpavel

import com.sushkpavel.controller.configureCheckerController
import com.sushkpavel.di.configureKoin
import com.sushkpavel.plugins.configureSerialization
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureKoin()
    configureCheckerController()
}