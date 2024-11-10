package com.sushkpavel.application.plugins

import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
//            single {
//                SolutionService(get())
//            }
//            single {
//                SolutionCheckerManager(get())
//            }
        })
    }
}


fun notMain() = (1..31).map { (0..100).random() }.sorted().forEach(::println)
