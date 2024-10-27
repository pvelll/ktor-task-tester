package com.sushkpavel.application.plugins

import com.sushkpavel.application.grpc.CompileClient
import com.sushkpavel.domain.repositories.CodeTestRepository
import com.sushkpavel.infrastructure.persistance.CodeTestRepositoryImpl
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
            factory<CodeTestRepository> {
                CodeTestRepositoryImpl()
            }
        })
    }
}


fun notMain() = (1..31).map { (0..100).random() }.sorted().forEach(::println)
