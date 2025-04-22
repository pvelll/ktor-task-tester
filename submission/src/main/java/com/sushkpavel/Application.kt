package com.sushkpavel

import com.sushkpavel.di.submissionModule
import com.sushkpavel.di.testTaskModule
import com.sushkpavel.plugins.configureRouting
import com.sushkpavel.plugins.configureSerialization
import io.ktor.server.application.*
import koin.configureKoin
import security.configureSecurity

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    configureKoin(submissionModule, testTaskModule)
    configureSerialization()
    configureSecurity()
    configureRouting()
}
