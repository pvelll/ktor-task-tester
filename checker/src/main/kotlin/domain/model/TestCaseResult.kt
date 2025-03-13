package com.sushkpavel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TestCaseResult(
    val testId: String,
    val success: Boolean,
    val actualResult: String,
    val expectedResult: String? = null,
    val error: String? = null
) {
    companion object {
        fun timeout(testId: String, expected: String, timeout: Long) =
            TestCaseResult(
                testId = testId,
                success = false,
                actualResult = "Timeout after ${timeout}s",
                expectedResult = expected.trim(),
                error = "Execution timeout"
            )

        fun exception(testId: String, expected: String, e: Exception) =
            TestCaseResult(
                testId = testId,
                success = false,
                actualResult = "Error ${e.message}s",
                expectedResult = expected.trim(),
                error = "Execution failed with an exception: ${e.message}"
            )
        fun TestCaseResult.truncateError(maxLength: Int): TestCaseResult {
            return this.copy(
                actualResult = actualResult.take(maxLength),
                error = error?.take(maxLength)
            )
        }
    }
}
