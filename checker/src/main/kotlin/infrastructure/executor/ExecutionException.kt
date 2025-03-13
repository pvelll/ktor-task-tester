package com.sushkpavel.infrastructure.executor

class ExecutionException(
    override val message: String,
    val output: String? = null,
    val processType: String
) : RuntimeException(message)