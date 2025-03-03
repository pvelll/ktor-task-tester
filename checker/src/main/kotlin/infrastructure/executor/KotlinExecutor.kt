package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.*
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class KotlinExecutor : LanguageExecutor {

    override fun compile(code: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val className = "Solution$uuid"
        val sourceFileName = "$className.kt"

        val tempDir = Files.createTempDirectory("kotlin_executor")
        println("Временная директория создана: ${tempDir.toAbsolutePath()}")

        val sourceFilePath = tempDir.resolve(sourceFileName)

        val modifiedCode = code.replace("class Solution", "class $className")
        println("Код модифицирован для компиляции.")
        Files.write(sourceFilePath, modifiedCode.toByteArray())
        println("Код записан в файл: $sourceFilePath")

        val processBuilder = ProcessBuilder("kotlinc", sourceFileName, "-include-runtime", "-d", "$className.jar")
        processBuilder.directory(tempDir.toFile())
        println("Компиляция кода...")
        val process = processBuilder.start()
        val exitCode = process.waitFor()

        if (exitCode != 0) {
            val errorStream = process.errorStream.bufferedReader().readText()
            println("Ошибка компиляции: $errorStream")
            tempDir.toFile().deleteRecursively()
            throw CompilationException("Ошибка компиляции:\n$errorStream")
        }

        println("Код успешно скомпилирован.")
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

        val processBuilder = ProcessBuilder("java", "-jar", "$className.jar")
        processBuilder.directory(tempDir.toFile())
        processBuilder.redirectErrorStream(true)

        println("Запуск программы...")
        val process = processBuilder.start()

        process.outputStream.bufferedWriter().use { writer ->
            writer.write(input)
            writer.newLine()
            writer.flush()
        }
        println("Входные данные переданы в программу.")

        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()
        println("Вывод программы: $output")

        val success = exitCode == 0 && output == expectedOutput.trim()
        println("Результат выполнения: success = $success")

        val errorOutput = if (exitCode != 0) {
            val error = process.errorStream.bufferedReader().readText()
            println("Ошибка выполнения: $error")
            error
        } else {
            null
        }


        return TestCaseResult(
            testId = testId,
            success = success,
            actualResult = output,
            expectedResult = expectedOutput.trim(),
            error = errorOutput
        )
    }
}