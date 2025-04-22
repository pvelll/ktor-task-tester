package com.sushkpavel.di

import di.jwtModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        val config = environment.config
        val jwtConfig = mapOf(
            "jwt.domain" to config.property("jwt.domain").getString(),
            "jwt.audience" to config.property("jwt.audience").getString(),
            "jwt.realm" to config.property("jwt.realm").getString(),
            "jwt.secret" to config.property("jwt.secret").getString()
        )
        properties(jwtConfig)
        modules(jwtModule,serviceModule, repositoryModule)
    }
}