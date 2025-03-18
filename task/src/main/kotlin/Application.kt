package com.sushkpavel


import com.sushkpavel.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
//    configureKoin()
//    configureCheckerController()
}