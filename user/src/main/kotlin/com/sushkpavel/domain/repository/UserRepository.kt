package com.sushkpavel.domain.repository

import com.sushkpavel.domain.dto.UserDTO
import com.sushkpavel.domain.model.Role
import com.sushkpavel.domain.model.User


interface UserRepository {
    suspend fun create(user: UserDTO): Int?
    suspend fun read(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun delete(id: Int)
    suspend fun getUserByEmail(email: String) : User?
    suspend fun updateRole(id: Int, role: Role) : Int
}