package com.sushkpavel.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(repositoryModule, serviceModule, executorModule)
    }
}