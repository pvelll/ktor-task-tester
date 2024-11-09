package com.sushkpavel.application.grpc

import com.sushkpavel.CompileServiceGrpc
import com.sushkpavel.SolutionSubmission
import com.sushkpavel.TestResult
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class CompileClient {

    fun compileSolution(solution: SolutionSubmission): TestResult {
        val result = stub.compileSolution(solution)
        println(result)
        return result
    }

    fun shutdown() {
        channel.shutdown()
    }


    companion object {
        private val channel: ManagedChannel = try {
            ManagedChannelBuilder.forAddress("compiler-service", 8083)
                .usePlaintext()
                .build()
        } catch (e: Exception) {
            throw RuntimeException("Failed to initialize channel", e)
        }

        private val stub: CompileServiceGrpc.CompileServiceBlockingStub = try {
            CompileServiceGrpc.newBlockingStub(channel)
        } catch (e: Exception) {
            throw RuntimeException("Failed to initialize stub", e)
        }
    }
}
