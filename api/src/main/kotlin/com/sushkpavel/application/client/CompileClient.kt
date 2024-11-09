package com.sushkpavel.application.client



import com.sushkpavel.domain.models.SolutionSubmission
import com.sushkpavel.domain.models.TestResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

class CompileClient {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    fun compileSolution(solution: SolutionSubmission): TestResult = runBlocking {
        val response: HttpResponse = client.post("http://compiler-service:8083/compile") {
            contentType(ContentType.Application.Json)
            setBody(solution)
        }
        response.body()
    }

    fun shutdown() {
        client.close()
    }
}
//package com.sushkpavel.application.grpc
//
//import com.sushkpavel.CompileServiceGrpc
//import com.sushkpavel.SolutionSubmission
//import com.sushkpavel.TestResult
//import io.grpc.ManagedChannel
//import io.grpc.ManagedChannelBuilder
//
//class CompileClient {
//
//    fun compileSolution(solution: SolutionSubmission): TestResult {
//        val result = stub.compileSolution(solution)
//        println(result)
//        return result
//    }
//
//    fun shutdown() {
//        channel.shutdown()
//    }
//
//
//    companion object {
//        private val channel: ManagedChannel = try {
//            ManagedChannelBuilder.forAddress("compiler-service", 8083)
//                .usePlaintext()
//                .build()
//        } catch (e: Exception) {
//            throw RuntimeException("Failed to initialize channel", e)
//        }
//
//        private val stub: CompileServiceGrpc.CompileServiceBlockingStub = try {
//            CompileServiceGrpc.newBlockingStub(channel)
//        } catch (e: Exception) {
//            throw RuntimeException("Failed to initialize stub", e)
//        }
//    }
//}


