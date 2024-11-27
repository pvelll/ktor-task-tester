package com.sushkpavel.domain.repository

import com.sushkpavel.domain.model.User
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp


interface UserRepository {
    suspend fun create(user: User): Int
    suspend fun read(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun delete(id: Int)
    suspend fun login(email: String, password: String): User?


    object Users : Table() {
        val userId = integer("user_id").autoIncrement()
        val username = varchar("username", length = 255)
        val email = varchar("email", length = 255).uniqueIndex()
        val passwordHash = varchar("password_hash", length = 255)
        val createdAt = timestamp("created_at" ).defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)
        val updatedAt = timestamp("updated_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)

        override val primaryKey = PrimaryKey(userId)
        init {
            uniqueIndex(username)
            uniqueIndex(email)
        }
    }
}