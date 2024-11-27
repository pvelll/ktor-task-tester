package com.sushkpavel.infrastructure.repository

import com.sushkpavel.domain.model.Token
import com.sushkpavel.domain.repository.TokenRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.temporal.ChronoUnit
import com.sushkpavel.domain.repository.TokenRepository.Tokens
import com.sushkpavel.domain.repository.UserRepository.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TokenRepositoryImpl(private val database: Database) : TokenRepository {
    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }


    override suspend fun addToken(token: Token): Int = dbQuery {
        Tokens.insert {
            it[userId] = token.userId
            it[Tokens.token] = token.token
            it[createdAt] = token.createdAt
            it[expiresAt] = token.expiresAt
        }[Tokens.tokenId]
    }

    override suspend fun getTokenById(tokenId: Int): Token? = dbQuery {
        Tokens.selectAll().where { Tokens.tokenId eq tokenId }
            .mapNotNull { toToken(it) }
            .singleOrNull()
    }

    override suspend fun getTokenByValue(token: String): Token? = dbQuery {
        Tokens.selectAll().where { Tokens.token eq token }
            .mapNotNull { toToken(it) }
            .singleOrNull()
    }

    override suspend fun deleteToken(tokenId: Int): Boolean = dbQuery {
        Tokens.deleteWhere { Tokens.tokenId eq tokenId } > 0
    }

    override suspend fun generateToken(userId: Int): Token {
        val tokenValue = java.util.UUID.randomUUID().toString()
        val createdAt = Instant.now()
        val expiresAt = createdAt.plus(30, ChronoUnit.DAYS)
        val token = Token(0, userId, tokenValue, createdAt, expiresAt)
        val tokenId = addToken(token)
        return token.copy(tokenId = tokenId)
    }

    private fun toToken(row: ResultRow): Token =
        Token(
            tokenId = row[Tokens.tokenId],
            userId = row[Tokens.userId],
            token = row[Tokens.token],
            createdAt = row[Tokens.createdAt],
            expiresAt = row[Tokens.expiresAt]
        )

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
