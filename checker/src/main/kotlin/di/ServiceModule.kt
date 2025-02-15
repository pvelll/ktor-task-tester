package com.sushkpavel.di

import com.sushkpavel.domain.service.CheckerService
import com.sushkpavel.infrastructure.service.CheckerServiceImpl
import org.koin.dsl.module

val  serviceModule = module {
    single<CheckerService> {
        CheckerServiceImpl()
    }
}