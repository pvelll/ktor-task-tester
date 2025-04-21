package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.tasktester.entities.checker.TestCase
import com.sushkpavel.domain.model.TestCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withContext
import java.time.Duration

class TestRunner(
    private val timeout: Duration = Duration.ofSeconds(3),
    private val fileManager: FileManager
) {
    suspend fun runTests(
        executor: LanguageExecutor,
        compilationResult: String,
        testCases: List<TestCase>
    ): List<TestCaseResult> {
        return coroutineScope {
            testCases.map { testCase ->
                async { runSingleTest(executor, compilationResult, testCase) }
            }.awaitAll()
        }.also {
            fileManager.cleanup(compilationResult)
        }
    }

    private suspend fun runSingleTest(
        executor: LanguageExecutor,
        compilationResult: String,
        testCase: TestCase
    ): TestCaseResult {
        return withContext(Dispatchers.IO) {
            try {
                withTimeout(timeout) {
                    executor.execute(
                        compilationResult = compilationResult,
                        input = testCase.input,
                        testId = testCase.id.toString(),
                        expectedOutput = testCase.expOutput
                    )
                }
            } catch (e: TimeoutCancellationException) {
                TestCaseResult.timeout(testCase.id.toString(), testCase.expOutput, timeout.seconds)
            } catch (e: Exception) {
                TestCaseResult.exception(testCase.id.toString(), testCase.expOutput, e)
            }
        }
    }
}
