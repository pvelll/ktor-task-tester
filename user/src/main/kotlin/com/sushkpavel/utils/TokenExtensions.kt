package com.sushkpavel.utils

import io.ktor.server.application.*

fun getTokenFromHeader(call : ApplicationCall) : String?{
    val authorizationHeader = call.request.headers["Authorization"]
    return authorizationHeader?.takeIf { it.startsWith("Bearer ") }?.removePrefix("Bearer ")?.trim()
}