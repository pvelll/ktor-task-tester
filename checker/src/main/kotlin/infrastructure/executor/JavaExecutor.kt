package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.*
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
class JavaExecutor : LanguageExecutor {

    override fun compile(code: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val className = "Solution$uuid"
        val sourceFileName = "$className.java"

        val tempDir = Files.createTempDirectory("java_executor")
        println("temp dir: ${tempDir.toAbsolutePath()}")
        val sourceFilePath = tempDir.resolve(sourceFileName)

        val modifiedCode = code.replace("public class Solution", "public class $className")
        Files.write(sourceFilePath, modifiedCode.toByteArray())
        println("wrote code in file: $sourceFilePath")

        val processBuilder = ProcessBuilder("javac", sourceFileName)
        processBuilder.directory(tempDir.toFile())
        val process = processBuilder.start()
        val exitCode = process.waitFor()

        if (exitCode != 0) {
            val errorStream = process.errorStream.bufferedReader().readText()
            println("compilation error: $errorStream")
            tempDir.toFile().deleteRecursively()
            throw CompilationException("Ошибка компиляции:\n$errorStream")
        }

        println("finally compiled")
        return "$tempDir|$className"
    }

    override fun execute(
        compilationResult: String,
        input: String,
        testId: String,
        expectedOutput: String
    ): TestCaseResult {
        println("Начало выполнения тест-кейса: testId = $testId, input = $input, expectedOutput = $expectedOutput")

        val (tempDirPath, className) = compilationResult.split("|")
        val tempDir = Paths.get(tempDirPath)
        println("Используется временная директория: $tempDir")

        val processBuilder = ProcessBuilder("java", "-cp", tempDir.toString(), className)
        processBuilder.directory(tempDir.toFile())
        processBuilder.redirectErrorStream(true)

        println("Запуск программы...")
        val process = processBuilder.start()

        // Передача входных данных в программу через InputStream
        process.outputStream.bufferedWriter().use { writer ->
            writer.write(input)
            writer.newLine()
            writer.flush()
        }
        println("Входные данные переданы в программу.")

        // Чтение вывода программы
        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()
        println("Вывод программы: $output")

        // Проверяем успех выполнения теста
        val success = exitCode == 0 && output == expectedOutput.trim()
        println("Результат выполнения: success = $success")

        // Если была ошибка, читаем её
        val errorOutput = if (exitCode != 0) {
            val error = process.errorStream.bufferedReader().readText()
            println("Ошибка выполнения: $error")
            error
        } else {
            null
        }

        // Формируем результат тест-кейса
        return TestCaseResult(
            testId = testId,
            success = success,
            actualResult = output,
            expectedResult = expectedOutput.trim(),
            error = errorOutput
        )
    }
}