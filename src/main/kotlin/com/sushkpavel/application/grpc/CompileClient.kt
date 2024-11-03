package com.sushkpavel.application.grpc

import com.sushkpavel.CompileServiceGrpc
import com.sushkpavel.SolutionSubmission
import com.sushkpavel.TestResult
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class CompileClient {
    private val stub: CompileServiceGrpc.CompileServiceBlockingStub = CompileServiceGrpc.newBlockingStub(channel)

    fun compileSolution(solution: SolutionSubmission): TestResult {
        val result = stub.compileSolution(solution)
        println(result)
        return result
    }

    companion object {
        val channel = ManagedChannelBuilder.forTarget("localhost:8083").build()
    }
}

