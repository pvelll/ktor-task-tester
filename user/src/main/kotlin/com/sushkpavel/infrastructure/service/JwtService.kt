package com.sushkpavel.infrastructure.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sushkpavel.domain.model.User
import com.sushkpavel.plugins.security.JwtConfig


class JwtService(private val jwtConfig: JwtConfig) {

}
