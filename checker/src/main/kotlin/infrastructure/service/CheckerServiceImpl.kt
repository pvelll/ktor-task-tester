package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.domain.model.SubmissionRequest
import com.sushkpavel.domain.model.TestCase
import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.domain.model.TestResult
import com.sushkpavel.domain.service.CheckerService
import com.sushkpavel.infrastructure.executor.factory.LanguageExecutorFactory
import org.koin.java.KoinJavaComponent.inject

class CheckerServiceImpl : CheckerService {
    private val executorFactory = LanguageExecutorFactory()
    private val testRepository by inject<TestCasesRepository>(TestCasesRepository::class.java)

    override suspend fun checkTask(subRequest: SubmissionRequest): TestResult {
        val executor = executorFactory.build(subRequest.language)
            ?: throw IllegalArgumentException("Неподдерживаемый язык: ${subRequest.language}")

        val compilationResult = executor.compile(subRequest.code)
        val testCases = getTestCases(subRequest.taskId)
        val testCaseResults = mutableListOf<TestCaseResult>()
        var allTestsPassed = true

        for (testCase in testCases) {
            val testCaseResult = executor.execute(
                compilationResult,
                testCase.input,
                testCase.id.toString(),
                testCase.expOutput
            )

            testCaseResults.add(testCaseResult)

            if (!testCaseResult.success) {
                allTestsPassed = false
                break
            }
        }

        val finalMessage = if (allTestsPassed) "Все тесты пройдены успешно" else "Тесты не пройдены"
        val testResult = TestResult(success = allTestsPassed, actualResult = finalMessage, testId = subRequest.taskId.toString())

        return testResult
    }

    override suspend fun getTestCases(taskId: Int): List<TestCase> {
        return testRepository.getTestCasesByTaskId(taskId)
    }
}
