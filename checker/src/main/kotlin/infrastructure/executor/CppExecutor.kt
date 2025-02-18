package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.domain.model.TestResult

class CppExecutor: LanguageExecutor {
    override fun compile(code: String): String {
        TODO("Not yet implemented")
    }

    override fun execute(
        compilationResult: String,
        input: String,
        testId: String,
        expectedOutput: String
    ): TestCaseResult {
        TODO("Not yet implemented")
    }
}