package com.sushkpavel.plugins.security

data class JwtConfig(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)
