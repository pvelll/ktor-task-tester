package com.sushkpavel.di

import com.sushkpavel.domain.repo.TestTaskRepository
import com.sushkpavel.infrastructure.client.ClientFactory
import com.sushkpavel.infrastructure.repository.TestTaskRepositoryImpl
import org.koin.dsl.module

val testTaskModule = module {
    single {
        ClientFactory().build()
    }
    factory<TestTaskRepository>{
        TestTaskRepositoryImpl(get(), get())
    }
}