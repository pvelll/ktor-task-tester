package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.TestResult

class JavaExecutor : LanguageExecutor {
    override fun compile(code: String): String {
        TODO("Not yet implemented")
    }

    override fun execute(compilationResult: String, input: String): TestResult {
        TODO("Not yet implemented")
    }
}