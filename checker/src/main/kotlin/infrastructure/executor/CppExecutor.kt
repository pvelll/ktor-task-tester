package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.*
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class CppExecutor : LanguageExecutor {

    override fun compile(code: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val sourceFileName = "Solution$uuid.cpp"

        val tempDir = Files.createTempDirectory("cpp_executor")
        println("temp dir: ${tempDir.toAbsolutePath()}")
        val sourceFilePath = tempDir.resolve(sourceFileName)

        Files.write(sourceFilePath, code.toByteArray())
        println("wrote code in file: $sourceFilePath")

        val processBuilder = ProcessBuilder("g++", sourceFileName, "-o", "Solution$uuid")
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
        return "$tempDir|Solution$uuid"
    }

    override fun execute(
        compilationResult: String,
        input: String,
        testId: String,
        expectedOutput: String
    ): TestCaseResult {
        println("Начало выполнения тест-кейса: testId = $testId, input = $input, expectedOutput = $expectedOutput")

        val (tempDirPath, executableName) = compilationResult.split("|")
        val tempDir = Paths.get(tempDirPath)
        println("Используется временная директория: $tempDir")

        val processBuilder = ProcessBuilder("./$executableName")
        processBuilder.directory(tempDir.toFile())
        processBuilder.redirectErrorStream(true)

        println("Запуск программы...")
        val process = processBuilder.start()

        // Передача входных данных в программу через OutputStream
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
