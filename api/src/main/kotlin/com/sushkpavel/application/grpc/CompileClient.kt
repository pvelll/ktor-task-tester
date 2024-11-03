package com.sushkpavel.application.grpc

import com.sushkpavel.CompileServiceGrpc
import com.sushkpavel.SolutionSubmission
import com.sushkpavel.TestResult
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.internal.DnsNameResolverProvider

class CompileClient {
    private val channel: ManagedChannel = ManagedChannelBuilder.forAddress("compiler-service", 8083)
        .nameResolverFactory(DnsNameResolverProvider())
        .usePlaintext()
        .build()

    private val stub: CompileServiceGrpc.CompileServiceBlockingStub = CompileServiceGrpc.newBlockingStub(channel)

    fun compileSolution(solution: SolutionSubmission): TestResult {
        val result = stub.compileSolution(solution)
        println(result)
        return result
    }

    fun shutdown() {
        channel.shutdown()
    }
}