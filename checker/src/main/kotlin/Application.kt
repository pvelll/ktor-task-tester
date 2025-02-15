package com.sushkpavel

import com.sushkpavel.controller.configureCheckerController
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
   configureCheckerController()
}