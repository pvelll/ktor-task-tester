package com.sushkpavel.di

import com.sushkpavel.domain.service.TaskService
import com.sushkpavel.infrastructure.service.TaskServiceImpl
import org.koin.dsl.module

val serviceModule = module{
    single<TaskService> { TaskServiceImpl(get()) }
}