package com.sushkpavel.domain.repository

import com.sushkpavel.domain.model.User
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

interface UserRepository {
    suspend fun create(user: User): Int
    suspend fun read(id: Int): User?
    suspend fun update(id: Int, user: User)
    suspend fun delete(id: Int)


    object Users : Table() {
        val userId = integer("user_id").autoIncrement()
        val username = varchar("username", length = 255)
        val email = varchar("email", length = 255)
        val passwordHash = varchar("password_hash", length = 255)
        val createdAt = timestamp("created_at" ).default(Instant.now())
        val updatedAt = timestamp("updated_at").default(Instant.now())

        override val primaryKey = PrimaryKey(userId)
        init {
            uniqueIndex(username)
            uniqueIndex(email)
        }
    }
}