package com.sushkpavel.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TestCaseResult(
    val testId: String,
    val success: Boolean,
    val actualResult: String,
    val expectedResult: String? = null,
    val error: String? = null
){
    companion object{
        fun timeout(testId: String, expected: String, timeout: Long) =
            TestCaseResult(
                testId = testId,
                success = false,
                actualResult = "Timeout after ${timeout}s",
                expectedResult = expected.trim(),
                error = "Execution timeout"
            )
    }
}
