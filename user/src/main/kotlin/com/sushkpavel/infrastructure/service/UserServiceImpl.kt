package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.dto.Credentials
import com.sushkpavel.domain.dto.UserDTO
import com.sushkpavel.domain.model.Token
import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.repository.TokenRepository
import com.sushkpavel.domain.repository.UserRepository
import com.sushkpavel.domain.service.UserService
import io.ktor.http.HttpStatusCode

class UserServiceImpl(private val userRepository: UserRepository, private val tokenRepository: TokenRepository) :
    UserService {
    override suspend fun login(credentials: Credentials): Token? {
        val user = userRepository.getUserByEmail(credentials.email)
        return if (user != null && credentials.passwordHash == user.passwordHash) {
            tokenRepository.generateToken(user)
        } else {
            null
        }
    }

    override suspend fun logout(tokenValue: String): Boolean {
        val token = tokenRepository.getTokenByValue(tokenValue)
        return if (token != null) {
            tokenRepository.deleteToken(token.tokenId)
        } else {
            false
        }
    }

    override suspend fun register(user: UserDTO): Int? {
        return (if (getUserByEmail(user.email) == null) {
            if (userRepository.create(user) != HttpStatusCode.Conflict.value) {
                HttpStatusCode.Created.value
            } else {
                HttpStatusCode.Conflict.value
            }
        } else {
            HttpStatusCode.Conflict.value
        }) as Int?
    }

    override suspend fun getById(id: Int): User? {
        return userRepository.read(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    override suspend fun update(id: Int, user: User) {
        userRepository.update(id, user)
    }

    override suspend fun delete(id: Int) {
        userRepository.delete(id)
    }

    override suspend fun getTokenByValue(token: String): Token? {
        return tokenRepository.getTokenByValue(token)
    }
}


