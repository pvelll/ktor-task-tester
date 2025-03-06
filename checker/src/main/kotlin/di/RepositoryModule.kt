package com.sushkpavel.di

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.infrastructure.repository.TestCasesRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val repositoryModule = module {
    single<TestCasesRepository> { TestCasesRepositoryImpl(get()) }
    single<Database> {
        Database.connect(
            url = "jdbc:mysql://db:3306/ktor_task_tester",
            user = "service",
            driver = "com.mysql.cj.jdbc.Driver",
            password = "3277122228",
        )
    }

}
