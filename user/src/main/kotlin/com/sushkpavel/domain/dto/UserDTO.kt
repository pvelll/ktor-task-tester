package com.sushkpavel.domain.dto

import com.sushkpavel.domain.model.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    val username: String,
    val email: String,
    val passwordHash: String,
    val role: Role? = null,
)