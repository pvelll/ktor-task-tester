package com.sushkpavel

import com.sushkpavel.controller.configureSubmissionController
import com.sushkpavel.di.submissionModule
import com.sushkpavel.di.testTaskModule
import io.ktor.server.application.*
import koin.configureKoin
import security.configureSecurity
import serialization.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


fun Application.module() {
    configureKoin(submissionModule, testTaskModule)
    configureSerialization()
    configureSecurity()
    configureSubmissionController()
}
