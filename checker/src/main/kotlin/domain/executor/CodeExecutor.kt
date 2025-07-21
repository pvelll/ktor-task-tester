package com.sushkpavel.domain.executor

import com.sushkpavel.domain.model.TestCaseResult

interface CodeExecutor {
    fun compile(code: String): String
    fun execute(compilationResult: String, input: String, testId: String, expectedOutput: String): TestCaseResult
}
