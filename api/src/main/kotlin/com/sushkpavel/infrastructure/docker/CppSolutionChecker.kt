package com.sushkpavel.infrastructure.docker

import com.sushkpavel.domain.models.SolutionSubmission
import com.sushkpavel.domain.models.TestResult
import com.sushkpavel.domain.repositories.CodeTestRepository
import com.sushkpavel.domain.services.SolutionChecker
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

//
//class CppSolutionChecker(
//    private val testRepo: CodeTestRepository,
//) : SolutionChecker {
//
//    override suspend fun checkSolution(solution: SolutionSubmission): TestResult {
//        val tests = testRepo.getTestByTaskId(solution.taskId)
//        saveCodeToFile(solution.code)
//        saveInputToFile(listOf(solution.input))
//
//        val compileResult = compileSolution()
//        if (compileResult != "Compilation succeeded") {
//            val compileErrors = File("/app/shared/compile_errors.txt").readText()
//            return TestResult(
//                id = solution.id,
//                userId = solution.userId,
//                solutionId = solution.id,
//                testId = solution.taskId,
//                actualResult = compileErrors,
//                success = false
//            )
//        }
//
//        val actualResult = executeTests().joinToString("\n")
//        if (actualResult != tests?.expectedResult?.joinToString("\n")) {
//            cleanUpFiles()
//            return TestResult(
//                id = solution.id,
//                userId = solution.userId,
//                solutionId = solution.id,
//                testId = solution.taskId,
//                actualResult = "Bad result",
//                success = false
//            )
//        }
//
//        cleanUpFiles()
//        return TestResult(
//            id = solution.id,
//            userId = solution.userId,
//            solutionId = solution.id,
//            testId = solution.taskId,
//            actualResult = "All tests passed",
//            success = true
//        )
//    }
//
//    private fun saveCodeToFile(code: String) {
//        val file = File("/app/shared/solution.cpp")
//        file.writeText(code)
//    }
//
//    private fun saveInputToFile(inputData: List<String>) {
//        val file = File("/app/shared/input.txt")
//        file.writeText(inputData.joinToString("\n"))
//    }
//
//    private fun cleanUpFiles() {
//        listOf(
//            "/app/shared/solution.cpp",
//            "/app/shared/input.txt",
//            "/app/shared/output.txt",
//            "/app/shared/compile_errors.txt"
//        ).forEach { path ->
//            val file = File(path)
//            if (file.exists()) file.delete()
//        }
//    }
//
//    @Throws(Exception::class)
//    private fun compileSolution(): String {
//        val url = URL("http://172.19.0.3:8083/compile")
//        val connection = url.openConnection() as HttpURLConnection
//        connection.requestMethod = "POST"
//        connection.doOutput = true
//
//        val responseCode = connection.responseCode
//        connection.disconnect()
//
//        return if (responseCode == 200) "Compilation succeeded" else "Compilation failed"
//    }
//
//    @Throws(Exception::class)
//    private fun executeTests(): List<String> {
//        val url = URL("http://172.19.0.4:8084/test")
//        val connection = url.openConnection() as HttpURLConnection
//        connection.requestMethod = "POST"
//        connection.doOutput = true
//
//        val responseCode = connection.responseCode
//        connection.disconnect()
//
//        if (responseCode == 200) {
//            val file = File("/app/shared/output.txt")
//            val reader = BufferedReader(InputStreamReader(FileInputStream(file)))
//            val outputLines: MutableList<String> = ArrayList()
//            var line: String
//            while ((reader.readLine().also { line = it }) != null) {
//                outputLines.add(line.trim { it <= ' ' })
//            }
//            reader.close()
//            return outputLines
//        } else {
//            throw Exception("Failed to execute tests")
//        }
//    }
//}