package com.sushkpavel.infrastructure.executor.error

class ExecutionException(
    override val message: String,
    val output: String? = null,
    val processType: String
) : CompilationException(message)