package com.sushkpavel.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

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
        slf4jLogger()
        modules(userModule, jwtModule)
    }
}