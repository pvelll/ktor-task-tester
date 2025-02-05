package com.sushkpavel.infrastructure.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ClientFactory {
    fun build() : HttpClient {
        return HttpClient(CIO){
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }
}
