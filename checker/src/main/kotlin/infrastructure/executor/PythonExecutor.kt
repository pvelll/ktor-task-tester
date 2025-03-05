package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.TestCaseResult
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class PythonExecutor : LanguageExecutor {

    override fun compile(code: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val scriptFileName = "script_$uuid.py"

        val tempDir = Files.createTempDirectory("python_executor")
        println("Временная директория создана: ${tempDir.toAbsolutePath()}")

        val scriptFilePath = tempDir.resolve(scriptFileName)
        Files.write(scriptFilePath, code.toByteArray())
        println("Код записан в файл: $scriptFilePath")

        return "$tempDir|$scriptFileName"
    }

    override fun execute(
        compilationResult: String,
        input: String,
        testId: String,
        expectedOutput: String
    ): TestCaseResult {
        println("testId = $testId, input = $input, expectedOutput = $expectedOutput")

        val (tempDirPath, scriptFileName) = compilationResult.split("|")
        val tempDir = Paths.get(tempDirPath)
        println("temp dir: $tempDir")

        val processBuilder = ProcessBuilder("python3", scriptFileName)
        processBuilder.directory(tempDir.toFile())
        processBuilder.redirectErrorStream(true)

        val process = processBuilder.start()
        process.outputStream.bufferedWriter().use { writer ->
            writer.write(input)
            writer.newLine()
            writer.flush()
        }
        val output = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()
        println("Вывод программы: $output")
        val success = exitCode == 0 && output == expectedOutput.trim()
        println("success = $success")

        val errorOutput = if (exitCode != 0) {
            val error = process.errorStream.bufferedReader().readText()
            println("e: $error")
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