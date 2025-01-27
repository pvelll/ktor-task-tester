package com.sushkpavel.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sushkpavel.domain.dto.NotifyMessageDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import java.time.Instant

fun Application.configureSecurity() {
    val jwtConfig: JwtConfig by inject()

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
                val expiresAt = credential.payload.expiresAt?.time
                val currentTime = Instant.now().toEpochMilli()
                if (expiresAt != null && expiresAt > currentTime && credential.payload.audience.contains(jwtConfig.audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    NotifyMessageDTO(message = "Unauthorized", code = HttpStatusCode.Unauthorized.value)
                )
            }
        }
    }
}

