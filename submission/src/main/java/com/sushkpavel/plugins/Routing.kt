package com.sushkpavel.plugins

import com.sushkpavel.controller.configureSubmissionController
import io.ktor.server.application.*

fun Application.configureRouting(){
    configureSubmissionController()
}