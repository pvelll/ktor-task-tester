package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.CodeExecutor
import com.sushkpavel.domain.model.TestCaseResult
import com.sushkpavel.infrastructure.executor.error.ExecutionException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID
import kotlin.jvm.Throws

abstract class BaseTaskExecutor : CodeExecutor {
    protected abstract val languageName: String
    protected abstract val sourceExtension: String
    protected abstract val compileCommand: (String, Path) -> List<String>
    protected abstract val executeCommand: (String, Path) -> List<String>
    protected open val needCompilation: Boolean = true
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    protected open fun preprocessCode(code: String, className: String): String = code
    protected open fun postCompile(tempDir: Path, className: String) {}

    override fun compile(code: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val className = "Solution$uuid"
        val sourceFileName = "$className.$sourceExtension"
        val tempDir = Files.createTempDirectory("${languageName}_executor").apply {
            toFile().deleteOnExit()
        }
        val sourceFilePath = tempDir.resolve(sourceFileName)
        val processedCode = preprocessCode(code, className)

        Files.write(sourceFilePath, processedCode.toByteArray())

        if (needCompilation) {
            runProcess(compileCommand(sourceFileName, tempDir), tempDir, "compilation")
        }

        postCompile(tempDir, className)
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

        return try {
            val output = runProcess(executeCommand(className, tempDir), tempDir, "execution", input)
            TestCaseResult(
                testId = testId,
                success = output.trim() == expectedOutput.trim(),
                actualResult = output.trim(),
                expectedResult = expectedOutput.trim(),
                error = null
            )
        } catch (e: ExecutionException) {
            TestCaseResult(
                testId = testId,
                success = false,
                actualResult = e.output?.trim() ?: "",
                expectedResult = expectedOutput.trim(),
                error = e.message
            )
        }
    }

    @Throws(ExecutionException::class)
    protected fun runProcess(
        command: List<String>,
        workingDir: Path,
        processType: String,
        input: String? = null
    ): String {
        val process = ProcessBuilder(command)
            .directory(workingDir.toFile())
            .redirectErrorStream(true)
            .start()

        input?.let {
            process.outputStream.bufferedWriter().use { writer ->
                writer.write(it)
                writer.newLine()
                writer.flush()
            }
        }

        val output = process.inputStream.bufferedReader().readText()
        logger.debug("EXECUTING OUTPUT: $output")
        val exitCode : Int = process.waitFor()

        if (exitCode != 0) {
            throw ExecutionException(
                message = "$processType failed with exit code $exitCode",
                output = output,
                processType = processType
            )
        }

        return output
    }
}
