package com.sushkpavel.di

import com.sushkpavel.infrastructure.service.JwtService
import com.sushkpavel.plugins.security.JwtConfig
import org.koin.dsl.module

val jwtModule = module {
    single {
        JwtConfig(
            domain = getProperty("jwt.domain"),
            audience = getProperty("jwt.audience"),
            realm = getProperty("jwt.realm"),
            secret = getProperty("jwt.secret")
        )
    }
    single { JwtService(get()) }
}