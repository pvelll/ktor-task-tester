package com.sushkpavel.infrastructure.repository

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sushkpavel.domain.model.Token
import com.sushkpavel.domain.model.User
import com.sushkpavel.domain.repository.TokenRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.temporal.ChronoUnit
import com.sushkpavel.domain.repository.TokenRepository.Tokens
import com.sushkpavel.domain.repository.UserRepository.Users
import com.sushkpavel.plugins.security.JwtConfig
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class TokenRepositoryImpl(database: Database) : TokenRepository {
    init {
        transaction(database) {
            SchemaUtils.create(Tokens)
        }
    }


    override suspend fun addToken(token: Token): Int = dbQuery {
        Tokens.insert {
            it[userId] = token.userId
            it[Tokens.token] = token.token
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

    override suspend fun generateToken(user: User): Token {
        val jwtConfig by inject<JwtConfig>(JwtConfig::class.java)
        val createdAt = Instant.now()
        val expiresAt = createdAt.plus(30, ChronoUnit.DAYS)
        val tokenValue = JWT.create()
            .withAudience(jwtConfig.audience)
            .withIssuer(jwtConfig.domain)
            .withExpiresAt(Date.from(expiresAt))
            .withIssuedAt(Date.from(createdAt))
            .withSubject(user.userId.toString())
            .withClaim("username", user.username)
            .sign(Algorithm.HMAC256(jwtConfig.secret))

        val token = Token(0, user.userId, tokenValue, createdAt, expiresAt)
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
