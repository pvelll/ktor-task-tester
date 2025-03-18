package com.sushkpavel.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val repositoryModule = module {
    single<Database> {
        Database.connect(
            url = "jdbc:mysql://db:3306/ktor_task_tester",
            user = "service",
            driver = "com.mysql.cj.jdbc.Driver",
            password = "3277122228",
        )
    }
}