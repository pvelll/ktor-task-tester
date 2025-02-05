package com.sushkpavel.di

import com.sushkpavel.infrastructure.client.ClientFactory
import org.koin.dsl.module

val testTaskModule = module {
    single {
        ClientFactory().build()
    }
}