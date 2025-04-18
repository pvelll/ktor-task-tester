package com.sushkpavel.domain.repository

import com.sushkpavel.domain.model.Token
import com.sushkpavel.domain.model.User

interface TokenRepository {
    suspend fun addToken(token: Token): Int
    suspend fun getTokenById(tokenId: Int): Token?
    suspend fun getTokenByValue(token: String): Token?
    suspend fun deleteToken(tokenId: Int): Boolean
    suspend fun generateToken(user: User): Token
}