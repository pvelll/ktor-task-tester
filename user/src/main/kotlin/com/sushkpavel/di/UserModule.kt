package com.sushkpavel.di

import com.sushkpavel.domain.repository.TokenRepository
import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService
import com.sushkpavel.infrastructure.repository.TokenRepositoryImpl
import com.sushkpavel.infrastructure.repository.UserRepositoryImpl
import com.sushkpavel.infrastructure.service.UserServiceImpl
import org.koin.dsl.module


val userModule = module {
    single<UserService> {
        UserServiceImpl(get(), get())
    }
    factory<UserRepository> {
        UserRepositoryImpl(get())
    }
    factory<TokenRepository> {
        TokenRepositoryImpl(get())
    }
}
