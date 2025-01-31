package com.sushkpavel.di

import com.sushkpavel.domain.repo.SolutionRepository
import com.sushkpavel.infrastructure.repository.SolutionRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val submissionModule = module {
    factory<Database> {
        Database.connect(
            url = "jdbc:mysql://127.0.0.1:3306/ktor_task_tester",
            user = "root",
            driver = "com.mysql.cj.jdbc.Driver",
            password = "3277122228",
        )
    }

    factory<SolutionRepository>{
        SolutionRepositoryImpl(get())
    }
}