package com.sushkpavel.plugins.security

import com.sushkpavel.tasktester.entities.user.Role
import io.ktor.server.auth.*

data class UserPrincipal(
    val userId: Int,
    val username: String,
    val role: Role
) : Principal
