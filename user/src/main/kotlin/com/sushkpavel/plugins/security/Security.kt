package com.sushkpavel.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sushkpavel.domain.repository.TokenRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.configureSecurity() {
    val jwtConfig: JwtConfig by inject()
    val tokenRepository: TokenRepository by inject()
    authentication {
        jwt {
            realm = jwtConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.domain)
                    .build()
            )
            validate { credential ->
                val token = credential.payload.getClaim("token").asString()
                val tokenRecord = tokenRepository.getTokenByValue(token)
                if (tokenRecord != null && tokenRecord.expiresAt.isAfter(Instant.now()) && credential.payload.audience.contains(
                        jwtConfig.audience
                    )
                ) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
