package com.sushkpavel.di

import com.sushkpavel.domain.repo.SolutionRepository
import com.sushkpavel.domain.service.SubmissionService
import com.sushkpavel.infrastructure.repository.SolutionRepositoryImpl
import com.sushkpavel.infrastructure.service.SubmissionServiceImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val submissionModule = module {
    single<Database> {
        Database.connect(
            url = "jdbc:mysql://db:3306/ktor_task_tester",
            user = "service",
            driver = "com.mysql.cj.jdbc.Driver",
            password = "3277122228",
        )
    }

    factory<SubmissionService>{
        SubmissionServiceImpl(get(),get())
    }

    factory<SolutionRepository>{
        SolutionRepositoryImpl(get())
    }
}