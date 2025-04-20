package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.domain.model.TestResult
import com.sushkpavel.infrastructure.executor.error.CompilationException
import com.sushkpavel.infrastructure.executor.error.ExecutionException
import kotlin.jvm.Throws

class ResultBuilder(private val maxErrorLength: Int = 254) {
    fun buildFinalResult(results: List<TestCaseResult>, taskId: Long) = TestResult(
        success = results.all { it.success },
        actualResult = if (results.all { it.success }) "All test passed" else "Fail on the tests",
        testId = taskId.toString()
    )

    fun compilationError(taskId: Long, e: ExecutionException) = TestResult(
        success = false,
        actualResult = "Compilation error: ${e.message.truncate()} , ${e.output}",
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

@Throws(CompilationException::class)
inline fun <R> ResultBuilder.handleCompilation(block: () -> R): R?  {
    return block()
}

inline fun <R> ResultBuilder.handleTestCases(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}