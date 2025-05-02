package security.jwt

import entities.user.Role
import io.ktor.server.auth.Principal

data class UserPrincipal(
    val userId: Int,
    val username: String,
    val role: Role
) : Principal