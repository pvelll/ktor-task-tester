package com.sushkpavel.infrastructure.executor.impl

import com.sushkpavel.infrastructure.executor.BaseLanguageExecutor
import java.nio.file.Path

class CppExecutor : BaseLanguageExecutor() {
    override val languageName = "cpp"
    override val sourceExtension = "cpp"
    override val needCompilation = true

    override val compileCommand = { source: String, dir: Path ->
        listOf("g++", source, "-o", dir.resolve("Solution").toString())
    }

    override val executeCommand = { _: String, dir: Path ->
        listOf(dir.resolve("Solution").toString())
    }
}
