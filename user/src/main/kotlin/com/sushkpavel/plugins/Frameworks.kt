package com.sushkpavel.plugins

import com.sushkpavel.domain.service.UserService
import com.sushkpavel.infrastructure.service.UserServiceImpl
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            factory<UserService> {
                UserServiceImpl(get())
            }
            factory<Database> {
                Database.connect(
                    url = "jdbc:mysql://0.0.0.0:3306/ktor_task_tester",
                    user = "root",
                    driver = "om.mysql.cj.jdbc.Driver",
                    password = "3277122228",
                )
            }
        })
    }
}
