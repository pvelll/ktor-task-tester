package com.sushkpavel.domain.model

import kotlinx.serialization.Serializable



@Serializable
data class TestResult(
    val testId: String,
    val actualResult: String,
    val success: Boolean
) {
    companion object {
        fun error(message: String) =
            TestResult(success = false, actualResult = message, testId = "")

        fun fromTestCaseResults(results: List<TestCaseResult>) =
            TestResult(
                success = results.all { it.success },
                actualResult = if (results.all { it.success }) "All tests passed" else "Some tests failed",
                testId = results.joinToString { it.testId }
            )


    }
}
