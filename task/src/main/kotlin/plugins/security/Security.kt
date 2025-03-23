package com.sushkpavel.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

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
                val userId = credential.payload.subject?.toIntOrNull()
                val username = credential.payload.getClaim("username").asString()
                val roleString = credential.payload.getClaim("role").asString()
                val role = roleString?.let { Role.valueOf(it) }

                if (userId != null && username != null && role != Role.USER && role != null) {
                    UserPrincipal(userId, username, role)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                HttpStatusCode.Unauthorized.let {
                    call.respond(
                        it
                    )
                }
            }
        }
    }
}

