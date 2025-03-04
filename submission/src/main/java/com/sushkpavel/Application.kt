package com.sushkpavel

import com.sushkpavel.di.configureKoin
import com.sushkpavel.plugins.configureRouting
import com.sushkpavel.plugins.configureSerialization
import com.sushkpavel.plugins.security.configureSecurity
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    configureKoin()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
