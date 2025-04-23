package koin

import dataBaseModule
import security.jwtConfig
import io.ktor.server.application.Application
import org.koin.core.context.GlobalContext.startKoin


fun Application.configureKoin(vararg modules: org.koin.core.module.Module) {
    startKoin {
        modules(jwtConfig, dataBaseModule, *modules)
    }
}


