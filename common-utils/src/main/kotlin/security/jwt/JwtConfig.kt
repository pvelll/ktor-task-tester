package com.sushkpavel.tasktester.security

data class JwtConfig(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)
