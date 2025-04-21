package com.sushkpavel.infrastructure.service

import com.sushkpavel.domain.TestCasesRepository
import com.sushkpavel.domain.model.*
import com.sushkpavel.domain.service.CheckerService
import com.sushkpavel.infrastructure.executor.ResultBuilder
import com.sushkpavel.infrastructure.executor.TestRunner
import com.sushkpavel.infrastructure.executor.error.CompilationException
import com.sushkpavel.infrastructure.executor.error.ExecutionException
import com.sushkpavel.infrastructure.executor.factory.LanguageExecutorFactory
import com.sushkpavel.infrastructure.executor.handleCompilation
import com.sushkpavel.infrastructure.executor.handleTestCases
import com.sushkpavel.infrastructure.model.SubmissionRequest
import com.sushkpavel.infrastructure.model.TestCaseDTO

class CheckerServiceImpl(
    private val testRepository: TestCasesRepository,
    private val executorFactory: LanguageExecutorFactory,
    private val testRunner: TestRunner,
    private val resultBuilder: ResultBuilder
) : CheckerService {

    override suspend fun checkTask(subRequest: SubmissionRequest): TestResult {
        return try {
            val executor = executorFactory.build(subRequest.language)

            val compilationResult = try {
                resultBuilder.handleCompilation {
                    executor.compile(subRequest.code)
                }
            } catch (e: ExecutionException) {
                return resultBuilder.compilationError(subRequest.taskId, e)
            }
            val testCases = try {
                resultBuilder.handleTestCases {
                    getTestCases(subRequest.taskId)
                }
            } catch (e: ExecutionException) {
                return resultBuilder.testCasesError(subRequest.taskId, e)
            }
            testRunner.runTests(executor, compilationResult.toString(), testCases!!)
                .let { resultBuilder.buildFinalResult(it, subRequest.taskId) }

        } catch (e: Exception) {
            resultBuilder.unexpectedError(e, subRequest.taskId)
        }
    }

    override suspend fun getTestCases(taskId: Long) = testRepository.getTestCasesByTaskId(taskId)
    override suspend fun postTestCases(testCaseDTO: TestCaseDTO) = testRepository.postTestCases(testCaseDTO)
}
