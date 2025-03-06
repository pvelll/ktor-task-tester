package com.sushkpavel.di

import com.sushkpavel.domain.repository.TokenRepository
import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService
import com.sushkpavel.infrastructure.repository.TokenRepositoryImpl
import com.sushkpavel.infrastructure.repository.UserRepositoryImpl
import com.sushkpavel.infrastructure.service.UserServiceImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module


val userModule = module {
    single<UserService> {
        UserServiceImpl(get(), get())
    }
    single<Database> {
        Database.connect(
//            url = "jdbc:mysql://db:3306/ktor_task_tester",
            url = "jdbc:mysql://192.168.1.7:3306/ktor_task_tester",
            user = "service",
            driver = "com.mysql.cj.jdbc.Driver",
            password = "3277122228",
        )
    }
    factory<UserRepository> {
        UserRepositoryImpl(get())
    }
    factory<TokenRepository> {
        TokenRepositoryImpl(get())
    }
}
