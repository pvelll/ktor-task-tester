package com.sushkpavel.domain.dto

data class UserDTO (
    val username: String,
    val email: String,
    val passwordHash: String,
)