package com.sushkpavel.domain.repository

import com.sushkpavel.tasktester.entities.user.Token
import com.sushkpavel.tasktester.entities.user.User

interface TokenRepository {
    suspend fun addToken(token: Token): Int
    suspend fun getTokenById(tokenId: Int): Token?
    suspend fun getTokenByValue(token: String): Token?
    suspend fun deleteToken(tokenId: Int): Boolean
    suspend fun generateToken(user: User): Token
}