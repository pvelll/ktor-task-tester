package com.sushkpavel.domain.executor

import com.sushkpavel.domain.model.TestResult

interface LanguageExecutor {
    fun compile(code: String): String
    fun execute(compilationResult: String, input: String): TestResult
}
