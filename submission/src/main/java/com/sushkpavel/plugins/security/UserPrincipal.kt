package com.sushkpavel.plugins.security

import io.ktor.server.auth.*

data class UserPrincipal(
    val userId: Int,
    val username: String
) : Principal
