package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.domain.model.TestResult

class ResultBuilder(private val maxErrorLength: Int = 254) {
    fun buildFinalResult(results: List<TestCaseResult>, taskId: Long) = TestResult(
        success = results.all { it.success },
        actualResult = if (results.all { it.success }) "All test passed" else "Fail :(",
        testId = taskId.toString()
    )

    fun compilationError(taskId: Long, e: Exception) = TestResult(
        success = false,
        actualResult = "Compilation error: ${e.message?.truncate()}",
        testId = taskId.toString()
    )

    fun testCasesError(taskId: Long, e: Exception) = TestResult(
        success = false,
        actualResult = "Test cases error: ${e.message?.truncate()}",
        testId = taskId.toString()
    )

    fun unexpectedError(e: Exception, taskId: Long) = TestResult(
        success = false,
        actualResult = "Unexpected error: ${e.message?.truncate()}",
        testId = taskId.toString()
    )

    private fun String?.truncate() = this?.take(maxErrorLength) ?: "Unknown error"
}

inline fun <R> ResultBuilder.handleCompilation(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

inline fun <R> ResultBuilder.handleTestCases(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}