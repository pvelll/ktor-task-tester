package com.sushkpavel.infrastructure.dto

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val email : String,
    val passwordHash: String
)