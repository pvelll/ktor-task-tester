package com.sushkpavel.domain.repository

import com.sushkpavel.infrastructure.dto.UserDTO
import entities.user.Role
import com.sushkpavel.tasktester.entities.user.User


interface UserRepository {
    suspend fun create(user: UserDTO): Int?
    suspend fun read(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun delete(id: Int)
    suspend fun getUserByEmail(email: String) : User?
    suspend fun updateRole(id: Int, role: Role) : Int
}