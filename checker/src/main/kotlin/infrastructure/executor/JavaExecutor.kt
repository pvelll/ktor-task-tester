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
        // Генерируем уникальное имя класса
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val className = "Solution$uuid"
        val sourceFileName = "$className.java"

        // Создаём временную директорию
        val tempDir = Files.createTempDirectory("java_executor")

        // Путь к файлу исходного кода
        val sourceFilePath = tempDir.resolve(sourceFileName)

        // Заменяем объявление класса на уникальное имя
        val modifiedCode = code.replace("public class Solution", "public class $className")

        // Записываем код в файл
        Files.write(sourceFilePath, modifiedCode.toByteArray())

        // Компилируем код
        val processBuilder = ProcessBuilder("javac", sourceFileName)
        processBuilder.directory(tempDir.toFile())
        val process = processBuilder.start()
        val exitCode = process.waitFor()

        // Проверяем на ошибки компиляции
        if (exitCode != 0) {
            val errorStream = process.errorStream.bufferedReader().readText()
            // Удаляем временные файлы
            tempDir.toFile().deleteRecursively()
            throw CompilationException("Ошибка компиляции:\n$errorStream")
        }

        // Возвращаем информацию о компиляции (путь и имя класса)
        return "$tempDir|$className"
    }

    override fun execute(
        compilationResult: String,
        input: String,
        testId: String,
        expectedOutput: String
    ): TestCaseResult {
        val (tempDirPath, className) = compilationResult.split("|")
        val tempDir = Paths.get(tempDirPath)

        val processBuilder = ProcessBuilder("java", "-cp", tempDir.toString(), className)
        processBuilder.directory(tempDir.toFile())
        processBuilder.redirectErrorStream(true)

        val process = processBuilder.start()

        // Передача входных данных в программу
        process.outputStream.bufferedWriter().use { writer ->
            writer.write(input)
            writer.flush()
        }

        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()

        // Удаляем временные файлы
        tempDir.toFile().deleteRecursively()

        // Проверяем успех выполнения теста
        val success = exitCode == 0 && output == expectedOutput.trim()

        // Если была ошибка, читаем её
        val errorOutput = if (exitCode != 0) {
            process.errorStream.bufferedReader().readText()
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
