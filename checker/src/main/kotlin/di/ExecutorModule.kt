package com.sushkpavel.di

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.infrastructure.executor.FileManager
import com.sushkpavel.infrastructure.executor.ResultBuilder
import com.sushkpavel.infrastructure.executor.TestRunner
import com.sushkpavel.infrastructure.executor.factory.LanguageExecutorFactory
import org.koin.dsl.module

val executorModule = module {
    single {
        LanguageExecutorFactory()
    }
    single{
        TestRunner(fileManager = get())
    }
    single{
        FileManager()
    }
    single{
        ResultBuilder()
    }
}
