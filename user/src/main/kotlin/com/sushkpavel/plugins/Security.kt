package com.sushkpavel.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val jwtConfig = environment.config.config("jwt")
    authentication {
        jwt {
            realm = jwtConfig.property("realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.property("secret").getString()))
                    .withAudience(jwtConfig.property("audience").getString())
                    .withIssuer(jwtConfig.property("domain").getString())
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtConfig.property("audience").getString())) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
