package com.sushkpavel.di

import com.sushkpavel.domain.repository.TaskRepository
import com.sushkpavel.infrastructure.repository.TaskRepositoryImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val repositoryModule = module {
    factory<TaskRepository> { TaskRepositoryImpl(get()) }
}