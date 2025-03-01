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
        // Генерируем уникальное имя класса
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val className = "Solution$uuid"
        val sourceFileName = "$className.kt"

        // Создаём временную директорию
        val tempDir = Files.createTempDirectory("kotlin_executor")
        println("Временная директория создана: ${tempDir.toAbsolutePath()}")

        // Путь к файлу исходного кода
        val sourceFilePath = tempDir.resolve(sourceFileName)

        // Заменяем объявление класса на уникальное имя
        val modifiedCode = code.replace("class Solution", "class $className")
        println("Код модифицирован для компиляции.")

        // Записываем код в файл
        Files.write(sourceFilePath, modifiedCode.toByteArray())
        println("Код записан в файл: $sourceFilePath")

        // Компилируем код
        val processBuilder = ProcessBuilder("kotlinc", sourceFileName, "-include-runtime", "-d", "$className.jar")
        processBuilder.directory(tempDir.toFile())
        println("Компиляция кода...")
        val process = processBuilder.start()
        val exitCode = process.waitFor()

        // Проверяем на ошибки компиляции
        if (exitCode != 0) {
            val errorStream = process.errorStream.bufferedReader().readText()
            println("Ошибка компиляции: $errorStream")
            // Удаляем временные файлы
            tempDir.toFile().deleteRecursively()
            throw CompilationException("Ошибка компиляции:\n$errorStream")
        }

        println("Код успешно скомпилирован.")
        // Возвращаем информацию о компиляции (путь и имя класса)
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

        // Запуск скомпилированного Kotlin-кода
        val processBuilder = ProcessBuilder("java", "-jar", "$className.jar")
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