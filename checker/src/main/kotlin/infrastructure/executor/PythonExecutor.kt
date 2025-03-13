package com.sushkpavel.infrastructure.executor

import com.sushkpavel.domain.executor.LanguageExecutor
import com.sushkpavel.domain.model.TestCaseResult
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class PythonExecutor : BaseLanguageExecutor() {
    override val languageName = "python"
    override val sourceExtension = "py"
    override val needCompilation = false
    override val compileCommand = { _: String, _: Path -> emptyList<String>() }

    override val executeCommand = { className: String, dir: Path ->
        listOf("python3", dir.resolve(className).toString())
    }

    override fun compile(code: String): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val fileName = "script_$uuid.py"
        val tempDir = Files.createTempDirectory("python_executor").apply {
            toFile().deleteOnExit()
        }
        val scriptFile = tempDir.resolve(fileName)
        Files.write(scriptFile, code.toByteArray())
        return "$tempDir|$fileName"
    }
}