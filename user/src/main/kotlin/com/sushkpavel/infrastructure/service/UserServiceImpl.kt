package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService

class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override suspend fun create(user: User): Int {
        return userRepository.create(user)
    }

    override suspend fun read(id: Int): User? {
        return userRepository.read(id)
    }

    override suspend fun update(id: Int, user: User) {
        userRepository.update(id, user)
    }

    override suspend fun delete(id: Int) {
        userRepository.delete(id)
    }
}


