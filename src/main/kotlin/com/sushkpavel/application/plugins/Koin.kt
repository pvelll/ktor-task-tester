package com.sushkpavel.application.plugins

import com.sushkpavel.application.services.SolutionService
import com.sushkpavel.domain.repositories.CodeTestRepository
import com.sushkpavel.infrastructure.docker.SolutionCheckerManager
import com.sushkpavel.infrastructure.persistance.CodeTestRepositoryImpl
import io.ktor.client.*
import io.ktor.http.cio.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single{
                SolutionService(get())
            }
            single{
                SolutionCheckerManager(get())
            }
            factory<CodeTestRepository> {
                CodeTestRepositoryImpl()
            }
        })
    }
}