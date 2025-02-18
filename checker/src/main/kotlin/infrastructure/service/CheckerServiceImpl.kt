package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.model.SubmissionRequest
import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.domain.model.TestResult
import com.sushkpavel.domain.service.CheckerService
import com.sushkpavel.infrastructure.executor.factory.LanguageExecutorFactory
import org.koin.java.KoinJavaComponent.inject

class CheckerServiceImpl : CheckerService {
    private val executorFactory = LanguageExecutorFactory()
    private val testRepository = TestRepositoryImpl() // Предполагаем, что этот репозиторий реализован

    override suspend fun checkTask(subRequest: SubmissionRequest): TestResult {
        val executor = executorFactory.build(subRequest.language)
            ?: throw IllegalArgumentException("Неподдерживаемый язык: ${subRequest.language}")

        // Компилируем код
        val compilationResult = executor.compile(subRequest.code)

        // Получаем тест-кейсы для задачи
        val testCases = getTestCases(subRequest.taskId)

        val testCaseResults = mutableListOf<TestCaseResult>()
        var allTestsPassed = true

        // Проверяем код на каждом тест-кейсе
        for (testCase in testCases) {
            val testCaseResult = executor.execute(
                compilationResult,
                testCase.input,
                testCase.id.toString(),
                testCase.expectedOutput
            )

            testCaseResults.add(testCaseResult)

            if (!testCaseResult.success) {
                allTestsPassed = false
                break // Останавливаемся при первом неудачном тесте
            }
        }

        // Формируем общий результат
        val finalMessage = if (allTestsPassed) "Все тесты пройдены успешно" else "Тесты не пройдены"
        val testResult = TestResult(success = allTestsPassed, actualResult = finalMessage, testId = subRequest.taskId.toString())

        // TODO сохранить testCaseResults в базе данных или логах

        return testResult
    }

    override suspend fun getTestCases(taskId: Int): List<TestCase> {
        // Метод для получения тест-кейсов из репозитория
        return testRepository.getTestCases(taskId)
    }
}
