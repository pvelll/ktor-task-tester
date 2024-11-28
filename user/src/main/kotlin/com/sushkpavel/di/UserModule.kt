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
    factory<UserService> {
        UserServiceImpl(get(), get())
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
    factory<TokenRepository> {
        TokenRepositoryImpl(get())
    }
}
