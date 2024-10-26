package com.sushkpavel

import com.sushkpavel.models.SolutionSubmission
import com.sushkpavel.models.TestResult
import com.sushkpavel.service.CompileServiceImpl
import io.grpc.ServerBuilder
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit

fun main() {
    val server = ServerBuilder.forPort(8083)
        .addService(CompileServiceImpl())
        .build()
        .start()
    server.awaitTermination()
}
