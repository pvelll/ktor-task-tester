package com.sushkpavel.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val role: String
)