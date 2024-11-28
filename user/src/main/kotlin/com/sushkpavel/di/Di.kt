package com.sushkpavel.di

import com.sushkpavel.domain.repository.TokenRepository
import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService
import com.sushkpavel.infrastructure.repository.TokenRepositoryImpl
import com.sushkpavel.infrastructure.repository.UserRepositoryImpl
import com.sushkpavel.infrastructure.service.UserServiceImpl
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(userModule, jwtModule)
    }
}