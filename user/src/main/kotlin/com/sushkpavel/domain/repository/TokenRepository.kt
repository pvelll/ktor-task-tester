package com.sushkpavel.domain.repository

import com.sushkpavel.domain.model.Token
import com.sushkpavel.domain.model.User
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit


interface TokenRepository {
    suspend fun addToken(token: Token): Int
    suspend fun getTokenById(tokenId: Int): Token?
    suspend fun getTokenByValue(token: String): Token?
    suspend fun deleteToken(tokenId: Int): Boolean
    suspend fun generateToken(user: User): Token


    object Tokens : Table() {
        val tokenId = integer("token_id").autoIncrement()
        val userId = integer("user_id").references(UserRepository.Users.userId, onDelete = ReferenceOption.CASCADE)
        val token = varchar("token", 255).uniqueIndex()
        val createdAt = timestamp("created_at").defaultExpression(org.jetbrains.exposed.sql.javatime.CurrentTimestamp)
        val expiresAt = timestamp("expires_at").clientDefault { Instant.now().plus(30, ChronoUnit.DAYS) }

        override val primaryKey = PrimaryKey(tokenId)
    }

}