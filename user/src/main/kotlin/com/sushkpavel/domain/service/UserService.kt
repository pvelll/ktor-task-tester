package com.sushkpavel.domain.service

import com.sushkpavel.infrastructure.dto.Credentials
import com.sushkpavel.infrastructure.dto.UserDTO
import com.sushkpavel.tasktester.entities.user.Token
import com.sushkpavel.tasktester.entities.user.User

interface UserService {
    suspend fun login(credentials: Credentials): Token?
    suspend fun logout(tokenValue: String) : Boolean
    suspend fun register(user: UserDTO): Int?
    suspend fun getById(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun getUserByEmail(email: String) : User?
    suspend fun delete(id: Int)
    suspend fun getTokenByValue(token : String) : Token?
}