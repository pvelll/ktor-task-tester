package com.sushkpavel.infrastructure.dto

import entities.user.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    val username: String,
    val email: String,
    val passwordHash: String,
    val role: Role? = null,
)