package com.sushkpavel.di

import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService
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
        modules(module {
            factory<UserService> {
                UserServiceImpl(get())
            }
            factory<Database> {
                Database.connect(
                    url = "jdbc:mysql://127.0.0.1:3306/ktor_task_tester",
                    user = "root",
                    driver = "com.mysql.cj.jdbc.Driver",
                    password = "3277122228",
                )
            }
            factory<UserRepository> {
                UserRepositoryImpl(get())
            }
        })
    }
}