package com.sushkpavel.di

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.infrastructure.repository.TestCasesRepositoryImpl

import org.koin.dsl.module

val repositoryModule = module {
    single<TestCasesRepository> { TestCasesRepositoryImpl(get()) }


}
