package com.sushkpavel.domain.service

import com.sushkpavel.domain.model.User

interface UserService {
    suspend fun login(email: String, password: String): User?
    suspend fun logout(token: String)
    suspend fun register(user: User): Int
    suspend fun getById(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun getUserByEmail(email: String)
    suspend fun delete(id: Int)
}