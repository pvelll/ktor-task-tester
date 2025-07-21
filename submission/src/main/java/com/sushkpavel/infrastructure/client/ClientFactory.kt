package com.sushkpavel.infrastructure.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class ClientFactory {
    fun build() : HttpClient {
        return HttpClient(CIO){
            engine {
                https {
                    trustManager = object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) { }
                        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) { }
                        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
                    }
                }
            } // temp, needed to be delete
            install(ContentNegotiation) {
                json(Json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
            }
        }
    }
}
