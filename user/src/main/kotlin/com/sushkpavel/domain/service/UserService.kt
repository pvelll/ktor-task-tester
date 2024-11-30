package com.sushkpavel.domain.service

import com.sushkpavel.domain.dto.Credentials
import com.sushkpavel.domain.dto.UserDTO
import com.sushkpavel.domain.model.Token
import com.sushkpavel.domain.model.User

interface UserService {
    suspend fun login(credentials: Credentials): Token?
    suspend fun logout(tokenValue: String) : Boolean
    suspend fun register(user: UserDTO): Int?
    suspend fun getById(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun getUserByEmail(email: String) : User?
    suspend fun delete(id: Int)
}