package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.repository.TokenRepository
import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService

class UserServiceImpl(private val userRepository: UserRepository, private val tokenRepository: TokenRepository) : UserService {
    override suspend fun login(email: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun logout(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(user: User): Int {
        return userRepository.create(user)
    }

    override suspend fun getById(id: Int): User? {
        return userRepository.read(id)
    }

    override suspend fun getUserByEmail(email: String) {
        userRepository.getUserByEmail(email)
    }

    override suspend fun update(id: Int, user: User) {
        userRepository.update(id, user)
    }

    override suspend fun delete(id: Int) {
        userRepository.delete(id)
    }
}


