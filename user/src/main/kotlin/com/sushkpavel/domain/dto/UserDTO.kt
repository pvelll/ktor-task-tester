package com.sushkpavel.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    val username: String,
    val email: String,
    val passwordHash: String,
)