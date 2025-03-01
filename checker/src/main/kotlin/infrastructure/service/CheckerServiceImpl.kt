package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.domain.model.CompilationException
import com.sushkpavel.domain.model.SubmissionRequest
import com.sushkpavel.domain.model.TestCase
import com.sushkpavel.domain.model.TestCaseDTO
import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.domain.model.TestResult
import com.sushkpavel.domain.service.CheckerService
import com.sushkpavel.infrastructure.executor.factory.LanguageExecutorFactory
import java.nio.file.Paths

class CheckerServiceImpl(val testRepository: TestCasesRepository) : CheckerService {
    private val executorFactory = LanguageExecutorFactory()

    override suspend fun checkTask(subRequest: SubmissionRequest): TestResult {
        val executor = executorFactory.build(subRequest.language)
            ?: throw IllegalArgumentException("unsupported lang: ${subRequest.language}")

        println("Компиляция кода...")
        val compilationResult = try {
            executor.compile(subRequest.code)
        } catch (e: CompilationException) {
            return TestResult(success = false, actualResult = "Compilation error: ${e.message}", testId = subRequest.taskId.toString())
        }

        val testCases = getTestCases(subRequest.taskId)

        val testCaseResults = mutableListOf<TestCaseResult>()
        var allTestsPassed = true

        // Получаем путь к временной директории
        val (tempDirPath, _) = compilationResult.split("|")
        val tempDir = Paths.get(tempDirPath)

        try {
            for (testCase in testCases) {
                println("testcase: $testCase")

                val testCaseResult = try {
                    executor.execute(compilationResult, testCase.input, testCase.id.toString(), testCase.expOutput)
                } catch (e: Exception) {
                    TestCaseResult(
                        testId = testCase.id.toString(),
                        success = false,
                        actualResult = "Ошибка выполнения: ${e.message}",
                        expectedResult = testCase.expOutput,
                        error = e.message
                    )
                }

                testCaseResults.add(testCaseResult)

                if (!testCaseResult.success) {
                    allTestsPassed = false
                    break
                }
            }
        } finally {
            tempDir.toFile().deleteRecursively()
            println("Временные файлы удалены.")
        }

        val finalMessage = if (allTestsPassed) "All test passed" else "Fail :("
        val testResult = TestResult(success = allTestsPassed, actualResult = finalMessage, testId = subRequest.taskId.toString())

        println("Проверка завершена. Результат: success = ${testResult.success}, message = ${testResult.actualResult}")
        return testResult
    }

    override suspend fun getTestCases(taskId: Int): List<TestCase> {
        return testRepository.getTestCasesByTaskId(taskId)
    }

    override suspend fun postTestCases(testCaseDTO: TestCaseDTO): Int {
        return testRepository.postTestCases(testCaseDTO)
    }
}