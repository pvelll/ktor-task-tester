package com.sushkpavel.di

import com.sushkpavel.domain.repo.SolutionRepository
import com.sushkpavel.domain.service.SubmissionService
import com.sushkpavel.infrastructure.repository.SolutionRepositoryImpl
import com.sushkpavel.infrastructure.service.SubmissionServiceImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val submissionModule = module {
    factory<SubmissionService>{
        SubmissionServiceImpl(get(),get())
    }

    factory<SolutionRepository>{
        SolutionRepositoryImpl(get())
    }
}