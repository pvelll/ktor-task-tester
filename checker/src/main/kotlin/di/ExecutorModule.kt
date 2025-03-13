package com.sushkpavel.di

import com.sushkpavel.infrastructure.executor.factory.LanguageExecutorFactory
import org.koin.dsl.module

val executorModule = module {
    single {
        LanguageExecutorFactory()
    }
}
