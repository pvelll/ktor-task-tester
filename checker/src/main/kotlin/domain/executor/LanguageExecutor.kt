package com.sushkpavel.domain.executor

import com.sushkpavel.domain.model.TestCaseResult

interface LanguageExecutor {
    fun compile(code: String): String
    fun execute(compilationResult: String, input: String, testId: String, expectedOutput: String): TestCaseResult
}
